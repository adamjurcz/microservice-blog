package org.commentary.presenter.controller;

import org.ajurcz.event.domain.Event;
import org.springframework.kafka.annotation.KafkaListener;

public interface EventConsumer {
    @KafkaListener(topics = "bad_comments_topic", groupId = "comment-consumer")
    void badCommentsListener(Event event);
}
