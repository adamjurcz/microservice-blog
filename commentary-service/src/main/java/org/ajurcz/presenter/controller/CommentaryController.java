package org.ajurcz.presenter.controller;

import org.ajurcz.core.domain.Commentary;
import org.ajurcz.core.usecase.CreateCommentaryUseCase;
import org.ajurcz.presenter.requests.CommentaryRequest;
import org.ajurcz.presenter.responses.CommentaryCreateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentaryController implements CommentaryResource{

    private final CreateCommentaryUseCase createCommentaryUseCase;

    public CommentaryController(CreateCommentaryUseCase createCommentaryUseCase) {
        this.createCommentaryUseCase = createCommentaryUseCase;
    }

    @Override
    public ResponseEntity<CommentaryCreateResponse> createCommentary(Integer postId, CommentaryRequest commentaryRequest) {
        Commentary commentary = createCommentaryUseCase
                .execute(new CreateCommentaryUseCase.Input(commentaryRequest.content(), postId))
                .getCommentary();
        return new ResponseEntity<>(new CommentaryCreateResponse(commentary.getId(),
                commentary.getContent(), commentary.getPostId()), HttpStatus.CREATED);
    }
}
