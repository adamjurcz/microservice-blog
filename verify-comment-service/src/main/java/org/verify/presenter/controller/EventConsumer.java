package org.verify.presenter.controller;

import org.ajurcz.event.domain.CommentDto;
import org.springframework.kafka.annotation.KafkaListener;

public interface EventConsumer {
    @KafkaListener(topics = "${commentToVerify.consumer.topic}", groupId = "verify-commentDto-consumer")
    void newCommentToVerifyListener(CommentDto commentDto);
}
