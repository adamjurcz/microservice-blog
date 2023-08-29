package org.commentary.presenter.controller;

import org.ajurcz.event.domain.CommentVerifiedDto;
import org.springframework.kafka.annotation.KafkaListener;

public interface EventConsumer {

    @KafkaListener(topics = "${badCommentary.consumer.topic}", groupId = "comment-commentDto-consumer")
    void badCommentsListener(CommentVerifiedDto commentVerifiedDto);
}
