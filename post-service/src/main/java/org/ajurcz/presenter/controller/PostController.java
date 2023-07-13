package org.ajurcz.presenter.controller;

import org.ajurcz.core.domain.Post;
import org.ajurcz.core.service.PostService;
import org.ajurcz.presenter.requests.PostRequest;
import org.ajurcz.presenter.responses.PostCreateResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PostController implements PostResource{
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /*
    @Override
    public ResponseEntity<AllPostsGetResponse> getPosts() {
        AllPostsGetResponse response = new AllPostsGetResponse(postService.getPosts());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostGetResponse> getPost(Integer postId) {
        PostGetResponse response = new PostGetResponse(postService.getPost(postId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     */

    @Override
    public ResponseEntity<PostCreateResponse> createPost(PostRequest postRequest) {
        Post post = postService.createPost(postRequest);
        PostCreateResponse response = new PostCreateResponse(post.getId(), post.getCreatorName(), post.getContent());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
