package org.commentary.dataprovider;

import org.ajurcz.event.domain.Event;
import org.commentary.core.service.EventSender;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventSenderImpl <T> implements EventSender <T> {
    private final KafkaTemplate<String, Event> kafkaTemplate;

    public EventSenderImpl(KafkaTemplate<String, Event> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendEvent(T dto) {
        Event event = new Event(dto.getClass().getName(), dto);
        String eventTopic = "event_topic";

        kafkaTemplate.send(eventTopic, event);
    }
}
