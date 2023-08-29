package org.commentary.presenter.controller;

import org.ajurcz.event.domain.CommentDto;
import org.commentary.core.domain.Commentary;
import org.commentary.core.service.EventSender;
import org.commentary.core.usecase.CreateCommentaryUseCase;
import org.commentary.presenter.requests.CommentaryRequest;
import org.commentary.presenter.responses.CommentaryCreateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentaryController implements CommentaryResource{

    @Value("${newCommentary.production.topic}")
    private String commentaryProductionTopicName;
    private final CreateCommentaryUseCase createCommentaryUseCase;
    private final EventSender eventSender;

    public CommentaryController(CreateCommentaryUseCase createCommentaryUseCase,
                                EventSender eventSender) {
        this.createCommentaryUseCase = createCommentaryUseCase;
        this.eventSender = eventSender;
    }

    @Override
    public ResponseEntity<CommentaryCreateResponse> createCommentary(Integer postId, CommentaryRequest commentaryRequest) {
        boolean isValid = true;
        Commentary commentary = createCommentaryUseCase
                .execute(new CreateCommentaryUseCase.Input(commentaryRequest.content(), postId, isValid))
                .getCommentary();

        CommentDto commentDto = new CommentDto(commentary.getId(), commentary.getContent(), commentary.getPostId());

        eventSender.sendToTopic(commentDto, commentaryProductionTopicName);

        return new ResponseEntity<>(new CommentaryCreateResponse(commentary.getId(),
                commentary.getContent(), commentary.getPostId()), HttpStatus.CREATED);
    }
}
