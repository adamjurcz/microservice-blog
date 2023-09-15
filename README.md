# microservice-blog
# Introduction
Idea of this service is pretty simple, all what's it have to do is create commentaries, posts and give customer chance
to read it later. We are also veryfing commentaries by some rules, ex. regex patterns. It's using microservices for 
later ability to:
- use kubernetes:
  - create independent instances of same service (replicas)
  - loadbalancing
  - self-healing of containers
- use asynchronous event driven architecture where different events can be handled on different service (ex. different machines)
- make it possible to use CQRS, where we have seperated reads and writes, in our case NoSQL mongodb for query from query-service and PostgreSQL for write into commentary and post services
- easier work for new people in case of service separation
- easier testing of application
- use event-store for later restore of databases in case of some failure

# Technologies
In java part of project:
- JDK 17
- Spring Boot 3.0.0
- Spring Data JPA
- Kafka Consumer/Producer/Stream API
- Jackson 2.15
- Maven

In kubernetes part of project:
- Ingress Controller (nginx)
- Skaffold
- MongoDB
- PostgreSQL
- Zookeeper and Kafka

# How to run app
The easiest way to run this app locally with all its functionalities is install minikube and deploy configured deployments and services which exists in manifest folder.
Second way is use docker compose, but I didn't implement this environment yet.
So you must:
- install docker
- install [minikube](https://minikube.sigs.k8s.io/docs/start/)
- install ingress controller on minikube
- apply manifest/init deployments and volume claims of databases, kafka and zookeeper in manifest/init folder so it will be running on minikube
- apply manifest deployments
- instead of two previous points you can use skaffold configuration for automatic build and deploy project, but firstly you must install skaffold and then you have to run 'skaffold dev' command from main folder
  - if topics are not avaible in kafka, you have to forward-ports to kafka and manually create topics which our app is using
  - -> kubectl get pods -> copy kafka deployment name -> port forward for kafka and ports 9093:9093 ex. from host machine -> create topics \
     `kafka-topics.sh --create --bootstrap-server localhost:[port-number] --replication-factor 1 --partitions 1 --topic [topic-name]`
  - topics in app: new_comments_topic, new_posts_topic, new_verified_comments_topic, validated_events_topic, bad_comments_topic, to_verify_comments_topic
# Description

## API endpoints
### 1. Get posts and commentaries
Describe: Customer can get all posts and commentaries commited to mongo database.

URL: /api/v1/queries/posts

Example curl: `curl --location 'http://adamjurczblog.com/api/v1/queries/posts'`

Response header:
- 200 OK

Response body:

<img width="800" alt="p1" src="https://github.com/adamjurcz/microservice-blog/assets/60117128/5a6ecfb0-0d11-44bf-8f02-f8350af06f01">

------------------------------------
### 2. Create posts
Describe: Customer can create post and persist it in postgres db.

URL: /api/v1/posts

Example curl: `curl --location 'http://adamjurczblog.com/api/v1/posts' \
--header 'Content-Type: application/json' \
--data '{
    "creatorName": "AdamJ",
    "content": "Post numer 1 Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, kurka quis nostrud"
}
'`

Response header:
- 200 OK

Response body:

<img width="800" alt="p1" src="https://github.com/adamjurcz/microservice-blog/assets/60117128/baba56c3-58f4-4304-a36e-e5a65b76983a">

------------------------------------
### 3. Create commentaries
Describe: Customer can create commentary and persist it in postgres db, but when this will not be correct, the query service would not get this comment.

URL: /api/v1/commentary/{postId}

Example curl: `curl --location 'http://adamjurczblog.com/api/v1/commentary/1052' \
--header 'Content-Type: application/json' \
--data '{
    "content" : "Komentarz Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, Kuka quis nostrud"
}'`

Response header:
- 200 OK

Response body:

<img width="800" alt="p1" src="https://github.com/adamjurcz/microservice-blog/assets/60117128/b4cc212c-b88c-45e9-ae7c-0ed9d9499105">

## Flow of messages between services
<img width="800" alt="p1" src="https://github.com/adamjurcz/microservice-blog/assets/60117128/fe66db84-0711-40f7-a0c6-a69b4aee4bb9">

All messages are handled by kafka, we keep default consumer config with auto.commit=true for not duplicating some messages and each service consumer has different group.id.

All messages go through event-service, where they are persisted in embedded event-store for later recovery in case of crash.

We are using CQRS architecture pattern.

Customer is able to send posts, which will travel through event-service to query-service. These posts are persisted in two databases- in query-service mongodb and in post-service postgres.
It looks similar for commentaries, but in this case messages will travel through verify-comment-service, where they are checked for validity and then event-service check if its valid - if not, 
it will send message to commentary-service and inform about this (commentary-service will update its db), or if its valid, event will send message to query-service with this comment.

## Deployment
### Ingress
We are using ingress controller in case of external access to service, like from host VM. It allows URL based HTTP routing and loadbalancing, and is also decoupled and isolated from our services.

If you have installed ingress controller like nginx, you can deploy our ingress-deployment.yml and ping at DNS name `adamjurczblog.com`, but firstly you must change ingress IP address for host name in `/etc/hosts`.

By using ingress we can also use different services paths with only one host, but in our case we are using only one service. Below you can see our case.

<img width="800" alt="p1" src="https://github.com/adamjurcz/microservice-blog/assets/60117128/d0c58024-2e61-4af5-b8f4-e9af107ab50f">

-------------------------------------------------------

### API deployments
Our API is stateless, and because of that we are able to set multiple replicas for each one. We are using different number of replicas depending on the purpose of it.

In deployments we have specified initContainers, where are checking for ability to communicate with kafka-service.

Services connected with deployments are default ClusterIP kubernetes services, which gives use ability to communicate with other services internal in cluster.

----------------------------------------------------------

### Stateful resources deployments
In more advanced project deploying this part should be made more carefully with respect to all cases.  
In kubernetes we can use StatefulSets to have persistent identity of different pods, so we are able to configure master-slave replication of different databases. By using kubernetes operators you can make administration of 
statful deployments easier. It will take part of replication between different pods and backups. 

In our case stateful resources are kafka, zookeeper, mongodb and postgres. In this projects all of these resources is normal deployment, because we are not using scaling nor more advanced features. We are persisting data on cluster by using Volume Claim mount path and host path. We can highlight important environment variable in kafka deployment `KAFKA_ADVERTISED_LISTENERS` values `INTERNAL://kafka-service:9092,EXTERNAL://localhost:9093` where kafka is listening for other agents (producers, consumers etc.) to connect into broker from outside this machine where broker is (like pod, or vm).

## Frontend
I also made a simple implementation of frontend to this API in Angular 16.1.6, which you can see [here](https://github.com/adamjurcz/microservice-blog-frontend).

Example screen:
![ScreenFromApp](https://github.com/adamjurcz/microservice-blog/assets/60117128/6359ec61-1201-436d-b115-1cfb75731d54)
----------------------------------
