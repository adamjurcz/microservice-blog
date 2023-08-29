package org.query.presenter.controller;


import org.ajurcz.event.domain.Event;
import org.springframework.kafka.annotation.KafkaListener;

public interface EventConsumer {
    @KafkaListener(topics = "${newValidatedEvent.consumer.topic}", groupId = "query-event-consumer")
    void eventListener(Event event);
}
