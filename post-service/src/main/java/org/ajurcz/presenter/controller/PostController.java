package org.ajurcz.presenter.controller;

import org.ajurcz.core.domain.Post;

import org.ajurcz.core.usecase.CreatePostUseCase;
import org.ajurcz.presenter.requests.PostRequest;
import org.ajurcz.presenter.responses.PostCreateResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PostController implements PostResource{
    private final CreatePostUseCase createPostUseCase;

    public PostController(CreatePostUseCase createPostUseCase) {
        this.createPostUseCase = createPostUseCase;
    }

    @Override
    public ResponseEntity<PostCreateResponse> createPost(PostRequest postRequest) {
        Post post = createPostUseCase
                .execute(new CreatePostUseCase.Input(postRequest.creatorName(), postRequest.content()))
                .getPost();

        return new ResponseEntity<>(
                new PostCreateResponse(post.getId(), post.getCreatorName(), post.getContent()),
                HttpStatus.CREATED);
    }
}
