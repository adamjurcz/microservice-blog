package org.ajurcz.presenter.controller;

import org.ajurcz.presenter.requests.PostRequest;
import org.ajurcz.presenter.responses.PostCreateResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PostResource.BASE_URL)
public interface PostResource {
    String BASE_URL = "/api/v1/posts";

    /*
    @GetMapping
    ResponseEntity<AllPostsGetResponse> getPosts();

    @GetMapping("/{postId}")
    ResponseEntity<PostGetResponse> getPost(@PathVariable Integer postId);
    */
    @PostMapping
    ResponseEntity<PostCreateResponse> createPost(@RequestBody PostRequest postRequest);

}
