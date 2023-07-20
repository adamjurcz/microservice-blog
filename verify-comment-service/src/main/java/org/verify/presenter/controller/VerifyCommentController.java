package org.verify.presenter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.event.core.domain.CommentDto;
import org.event.core.domain.CommentVerifiedDto;
import org.event.core.domain.Event;

import org.verify.core.service.EventSender;
import org.verify.core.usecase.VerifyCommentUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class VerifyCommentController implements VerifyCommentResource{

    private final EventSender<CommentVerifiedDto> eventSender;

    private final ObjectMapper objectMapper;

    private final VerifyCommentUseCase verifyCommentUseCase;

    public VerifyCommentController(EventSender<CommentVerifiedDto> eventSender, ObjectMapper objectMapper, VerifyCommentUseCase verifyCommentUseCase) {
        this.eventSender = eventSender;
        this.objectMapper = objectMapper;
        this.verifyCommentUseCase = verifyCommentUseCase;
    }

    @Override
    public ResponseEntity<Void> verifyComment(Event event) {
        try{
            Class<?> clazz = Class.forName(event.getClassName());
            Object object = objectMapper.convertValue(event.getObject(), clazz);
            if(object instanceof CommentDto commentDto){
                boolean isValid = verifyCommentUseCase
                        .execute(new VerifyCommentUseCase.Input(commentDto.getContent()))
                        .isValid();
                eventSender.sendEvent(new CommentVerifiedDto(commentDto.getId(), commentDto.getContent(), commentDto.getPostId(), isValid));
            }
        }
        catch(ClassNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
