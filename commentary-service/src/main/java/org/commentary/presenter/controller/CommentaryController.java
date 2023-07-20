package org.commentary.presenter.controller;

import org.event.core.domain.CommentDto;
import org.event.core.domain.Event;
import org.commentary.core.domain.Commentary;
import org.commentary.core.service.EventSender;
import org.commentary.core.usecase.CreateCommentaryUseCase;
import org.commentary.core.usecase.HandleEventUseCase;
import org.commentary.presenter.requests.CommentaryRequest;
import org.commentary.presenter.responses.CommentaryCreateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentaryController implements CommentaryResource{

    private final CreateCommentaryUseCase createCommentaryUseCase;

    private final HandleEventUseCase handleEventUseCase;

    private final EventSender<CommentDto> eventSender;

    public CommentaryController(CreateCommentaryUseCase createCommentaryUseCase,
                                HandleEventUseCase handleEventUseCase,
                                EventSender<CommentDto>eventSender) {
        this.createCommentaryUseCase = createCommentaryUseCase;
        this.handleEventUseCase = handleEventUseCase;
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

    @Override
    public ResponseEntity<Void> eventHandler(Event event) {
        handleEventUseCase.execute(new HandleEventUseCase.Input(event));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
