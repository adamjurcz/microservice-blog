package org.query.presenter.controller;


import org.ajurcz.event.domain.Event;
import org.springframework.kafka.annotation.KafkaListener;

public interface EventConsumer {
    @KafkaListener(topics = "validated_events_topic", groupId = "query-consumer")
    void eventListener(Event event);
}
