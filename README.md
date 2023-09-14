# microservice-blog
# Introduction
Idea of this service is pretty simple, all what's it have to do is create commentaries, posts and give customer chance
for read it later. We are also veryfing commentaries by some rules, ex. regex patterns. It's using microservices for 
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
  - -> kubectl get pods -> copy kafka deployment name -> port forward for kafka and ports 9093:9093 ex. from host machine `kafka-topics.sh --create --bootstrap-server localhost:[port-number] --replication-factor 1 --partitions 1 --topic [topic-name]`
  - topics in app: new_comments_topic, new_posts_topic, new_verified_comments_topic, validated_events_topic, bad_comments_topic, to_verify_comments_topic
# Description
## API endpoints
### Get Posts and Commentaries

## Flow of messages between services
<img width="800" alt="p1" src="https://github.com/adamjurcz/microservice-blog/assets/60117128/fe66db84-0711-40f7-a0c6-a69b4aee4bb9">
Customer can send
