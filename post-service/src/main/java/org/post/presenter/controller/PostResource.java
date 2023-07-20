package org.post.presenter.controller;

import org.post.presenter.requests.PostRequest;
import org.post.presenter.responses.PostCreateResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PostResource.BASE_URL)
public interface PostResource {
    String BASE_URL = "/api/v1/posts";

    @PostMapping
    ResponseEntity<PostCreateResponse> createPost(@RequestBody PostRequest postRequest);

}
