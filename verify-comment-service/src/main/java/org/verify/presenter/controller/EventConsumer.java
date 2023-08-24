package org.verify.presenter.controller;

import org.ajurcz.event.domain.Event;
import org.springframework.kafka.annotation.KafkaListener;

public interface EventConsumer {
    @KafkaListener(topics = "comment_not_verified_topic", groupId = "verify-comment-consumer")
    void commentToVerifyListener(Event event);
}
