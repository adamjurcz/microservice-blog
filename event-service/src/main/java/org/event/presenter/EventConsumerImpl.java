package org.event.presenter;

import org.ajurcz.event.domain.CommentDto;
import org.ajurcz.event.domain.CommentVerifiedDto;
import org.ajurcz.event.domain.PostDto;
import org.event.core.service.EventSender;
import org.event.core.usecase.AddToEventStoreUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventConsumerImpl implements EventConsumer{
    @Value("${newPost.producer.topic}")
    private String newPostsProducerTopicName;
    @Value("${badCommentary.producer.topic}")
    private String badCommentsProducerTopicName;
    @Value("${validCommentary.producer.topic}")
    private String validCommentsProducerTopicName;
    @Value("${commentToVerify.producer.topic}")
    private String toVerifyCommentsProducerTopicName;

    private final AddToEventStoreUseCase addToEventStoreUseCase;

    private final EventSender eventSender;

    public EventConsumerImpl(AddToEventStoreUseCase addToEventStoreUseCase, EventSender eventSender) {
        this.addToEventStoreUseCase = addToEventStoreUseCase;
        this.eventSender = eventSender;
    }

    @Override
    public void newCommentsListener(CommentDto commentDto) {
        eventSender.sendToTopic(commentDto, toVerifyCommentsProducerTopicName);
    }

    @Override
    public void newPostsListener(PostDto postDto) {
        addToEventStoreUseCase.execute(new AddToEventStoreUseCase.Input(postDto));
        eventSender.sendToTopic(postDto, newPostsProducerTopicName);
    }

    @Override
    public void newVerifiedCommentaryListener(CommentVerifiedDto commentVerifiedDto) {
        if(commentVerifiedDto.isValid()){
            addToEventStoreUseCase.execute(new AddToEventStoreUseCase.Input(commentVerifiedDto));
            eventSender.sendToTopic(commentVerifiedDto, validCommentsProducerTopicName);
        }
        else {
            eventSender.sendToTopic(commentVerifiedDto, toVerifyCommentsProducerTopicName);
        }
    }
}
