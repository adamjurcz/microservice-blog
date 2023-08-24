package org.verify.dataprovider;

import org.ajurcz.event.domain.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.ResourceAccessException;
import org.verify.core.service.EventSender;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
