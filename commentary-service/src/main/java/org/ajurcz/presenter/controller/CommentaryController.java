package org.ajurcz.presenter.controller;

import org.ajurcz.core.domain.Event;
import org.ajurcz.core.service.CommentaryService;
import org.ajurcz.presenter.requests.CommentaryRequest;
import org.ajurcz.presenter.responses.CommentaryCreateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentaryController implements CommentaryResource{

    private final CommentaryService commentaryService;

    public CommentaryController(CommentaryService commentaryService) {
        this.commentaryService = commentaryService;
    }

    @Override
    public ResponseEntity<CommentaryCreateResponse> createCommentary(Integer postId, CommentaryRequest commentaryRequest) {
        CommentaryCreateResponse response = new CommentaryCreateResponse(commentaryService.createCommentary(postId, commentaryRequest));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
