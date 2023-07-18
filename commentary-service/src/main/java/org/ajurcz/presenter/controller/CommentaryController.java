package org.ajurcz.presenter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ajurcz.core.domain.CommentDto;
import org.ajurcz.core.domain.CommentVerifiedDto;
import org.ajurcz.core.domain.Commentary;
import org.ajurcz.core.domain.Event;
import org.ajurcz.core.service.EventSender;
import org.ajurcz.core.usecase.CreateCommentaryUseCase;
import org.ajurcz.core.usecase.UpdateCommentaryUseCase;
import org.ajurcz.presenter.requests.CommentaryRequest;
import org.ajurcz.presenter.responses.CommentaryCreateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentaryController implements CommentaryResource{

    private final CreateCommentaryUseCase createCommentaryUseCase;

    private final UpdateCommentaryUseCase updateCommentaryUseCase;

    private final ObjectMapper objectMapper;

    private final EventSender<CommentDto> eventSender;

    public CommentaryController(CreateCommentaryUseCase createCommentaryUseCase,
                                UpdateCommentaryUseCase updateCommentaryUseCase, ObjectMapper objectMapper, EventSender<CommentDto>eventSender) {
        this.createCommentaryUseCase = createCommentaryUseCase;
        this.updateCommentaryUseCase = updateCommentaryUseCase;
        this.objectMapper = objectMapper;
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
        try {
            Class<?> clazz = Class.forName(event.getClassName());
            Object object = objectMapper.convertValue(event.getObject(), clazz);
            if (object instanceof CommentVerifiedDto commentVerifiedDto) {
                if(!commentVerifiedDto.isValid()){
                    updateCommentaryUseCase.execute(new UpdateCommentaryUseCase.Input(commentVerifiedDto.getId(),
                            commentVerifiedDto.getContent(),
                            commentVerifiedDto.getPostId(),
                            commentVerifiedDto.isValid()));
                }
            }
        }
        catch(ClassNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
