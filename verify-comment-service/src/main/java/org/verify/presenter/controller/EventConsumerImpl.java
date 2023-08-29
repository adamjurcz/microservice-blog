package org.verify.presenter.controller;

import org.ajurcz.event.domain.CommentDto;
import org.ajurcz.event.domain.CommentVerifiedDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.verify.core.service.EventSender;
import org.verify.core.usecase.VerifyCommentUseCase;

@Component
public class EventConsumerImpl implements EventConsumer{
    @Value("${newVerifiedCommentary.producer.topic}")
    private String newVerifiedCommentsTopicName;

    private final VerifyCommentUseCase verifyCommentUseCase;

    private final EventSender eventSender;

    public EventConsumerImpl(VerifyCommentUseCase verifyCommentUseCase, EventSender eventSender) {
        this.verifyCommentUseCase = verifyCommentUseCase;
        this.eventSender = eventSender;
    }

    @Override
    public void newCommentToVerifyListener(CommentDto commentDto) {
        boolean isValid = verifyCommentUseCase
                .execute(new VerifyCommentUseCase.Input(commentDto.getContent()))
                .isValid();
        CommentVerifiedDto commentVerifiedDto = new CommentVerifiedDto(commentDto.getId(), commentDto.getContent(),
                commentDto.getPostId(), isValid);

        eventSender.sendToTopic(commentVerifiedDto, newVerifiedCommentsTopicName);
    }
}
