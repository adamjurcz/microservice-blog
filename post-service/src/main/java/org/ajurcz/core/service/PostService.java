package org.ajurcz.core.service;

import org.ajurcz.core.domain.Event;
import org.ajurcz.core.domain.Post;
import org.ajurcz.core.domain.PostDto;
import org.ajurcz.presenter.requests.PostRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PostService {
    List<Post> posts = new ArrayList<>();

    private final RestTemplate restTemplate;

    public PostService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Post createPost(PostRequest postRequest){
        Integer newPostId;
        if(!posts.isEmpty()){
            newPostId = posts.stream().max(Comparator.comparing(Post::getId)).get().getId() + 1;
        }
        else{
            newPostId = 0;
        }
        Post post = new Post(newPostId, postRequest.creatorName(), postRequest.content());
        posts.add(post);

        PostDto postDto = new PostDto(post.getId(), post.getCreatorName(), post.getContent());
        Event postCreatedEvent = new Event(postDto.getClass().getName(), postDto);
        HttpEntity<Event> request = new HttpEntity<>(postCreatedEvent);
        String orchestratorUrl = "http://localhost:8083/api/v1/events";
        restTemplate
                .exchange(orchestratorUrl, HttpMethod.POST, request, Void.class);

        return post;
    }
}
