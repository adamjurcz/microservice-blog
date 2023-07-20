package org.post.presenter.controller;

import org.post.core.domain.Post;

import org.event.core.domain.PostDto;
import org.post.core.service.EventSender;
import org.post.core.usecase.CreatePostUseCase;
import org.post.presenter.requests.PostRequest;
import org.post.presenter.responses.PostCreateResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PostController implements PostResource{
    private final CreatePostUseCase createPostUseCase;

    private final EventSender<PostDto> eventSender;

    public PostController(CreatePostUseCase createPostUseCase, EventSender<PostDto> eventSender) {
        this.createPostUseCase = createPostUseCase;
        this.eventSender = eventSender;
    }

    @Override
    public ResponseEntity<PostCreateResponse> createPost(PostRequest postRequest) {
        Post post = createPostUseCase
                .execute(new CreatePostUseCase.Input(postRequest.creatorName(), postRequest.content()))
                .getPost();

        eventSender.sendEvent(new PostDto(post.getId(), post.getCreatorName(), post.getContent()));

        return new ResponseEntity<>(
                new PostCreateResponse(post.getId(), post.getCreatorName(), post.getContent()),
                HttpStatus.CREATED);
    }
}
