package org.event.presenter;

import org.ajurcz.event.domain.Event;
import org.springframework.kafka.annotation.KafkaListener;


public interface EventConsumer {
    @KafkaListener(topics = "event_topic", groupId = "event-consumer")
    void eventListener(Event event);
}
