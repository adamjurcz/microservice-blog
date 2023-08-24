package org.verify.presenter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ajurcz.event.domain.CommentDto;
import org.ajurcz.event.domain.CommentVerifiedDto;
import org.ajurcz.event.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.verify.core.service.EventSender;
import org.verify.core.usecase.VerifyCommentUseCase;

@Component
public class EventConsumerImpl implements EventConsumer{
    Logger logger = LoggerFactory.getLogger(EventConsumerImpl.class);

    private final EventSender<CommentVerifiedDto> eventSender;

    private final VerifyCommentUseCase verifyCommentUseCase;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, Event> kafkaTemplate;

    public EventConsumerImpl(EventSender<CommentVerifiedDto> eventSender, VerifyCommentUseCase verifyCommentUseCase, ObjectMapper objectMapper,
                             KafkaTemplate<String, Event> kafkaTemplate) {
        this.eventSender = eventSender;
        this.verifyCommentUseCase = verifyCommentUseCase;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void commentToVerifyListener(Event event) {
        String eventTopic = "event_topic";
        try{
            Class<?> clazz = Class.forName(event.getClassName());
            Object object = objectMapper.convertValue(event.getObject(), clazz);
            if(object instanceof CommentDto commentDto){
                logger.info("dostalismy komentarz do zweryfikowania");
                boolean isValid = verifyCommentUseCase
                        .execute(new VerifyCommentUseCase.Input(commentDto.getContent()))
                        .isValid();
                CommentVerifiedDto commentVerifiedDto = new CommentVerifiedDto(commentDto.getId(), commentDto.getContent(),
                        commentDto.getPostId(), isValid);

                eventSender.sendEvent(commentVerifiedDto);
            }
        }
        catch(ClassNotFoundException e){
            //TODO
        }
    }
}
