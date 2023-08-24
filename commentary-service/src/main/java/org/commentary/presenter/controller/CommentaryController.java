package org.commentary.presenter.controller;

import org.ajurcz.event.domain.CommentDto;
import org.commentary.core.domain.Commentary;
import org.commentary.core.service.EventSender;
import org.commentary.core.usecase.CreateCommentaryUseCase;
import org.commentary.presenter.requests.CommentaryRequest;
import org.commentary.presenter.responses.CommentaryCreateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentaryController implements CommentaryResource{

    private final CreateCommentaryUseCase createCommentaryUseCase;


    private final EventSender<CommentDto> eventSender;

    public CommentaryController(CreateCommentaryUseCase createCommentaryUseCase,
                                EventSender<CommentDto>eventSender) {
        this.createCommentaryUseCase = createCommentaryUseCase;
        this.eventSender = eventSender;
    }

    @Override
    public ResponseEntity<CommentaryCreateResponse> createCommentary(Integer postId, CommentaryRequest commentaryRequest) {
        boolean isValid = true;
        Commentary commentary = createCommentaryUseCase
                .execute(new CreateCommentaryUseCase.Input(commentaryRequest.content(), postId, isValid))
                .getCommentary();

        eventSender.sendEvent(new CommentDto(commentary.getId(), commentary.getContent(), commentary.getPostId()));

        return new ResponseEntity<>(new CommentaryCreateResponse(commentary.getId(),
                commentary.getContent(), commentary.getPostId()), HttpStatus.CREATED);
    }
}
