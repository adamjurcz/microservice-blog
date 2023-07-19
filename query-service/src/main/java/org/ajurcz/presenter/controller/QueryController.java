package org.ajurcz.presenter.controller;

import org.ajurcz.core.domain.*;
import org.ajurcz.core.usecase.GetAllPostsUseCase;
import org.ajurcz.core.usecase.HandleEventUseCase;
import org.ajurcz.presenter.response.PostGetAllResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryController implements QueryResource{
    private final HandleEventUseCase handleEventUseCase;

    private final GetAllPostsUseCase getAllPostsUseCase;

    public QueryController(HandleEventUseCase handleEventUseCase, GetAllPostsUseCase getAllPostsUseCase) {
        this.handleEventUseCase = handleEventUseCase;
        this.getAllPostsUseCase = getAllPostsUseCase;
    }

    @Override
    public ResponseEntity<Void> eventHandler(Event event) {
        handleEventUseCase.execute(new HandleEventUseCase.Input(event));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostGetAllResponse> getAllPosts() {
        List<Post> posts = getAllPostsUseCase.execute(new GetAllPostsUseCase.Input())
                .getPosts();
        return new ResponseEntity<>(new PostGetAllResponse(posts),HttpStatus.OK);
    }
}
