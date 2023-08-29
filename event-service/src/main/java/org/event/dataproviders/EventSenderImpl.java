package org.event.dataproviders;

import org.ajurcz.event.domain.Event;
import org.event.core.service.EventSender;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventSenderImpl implements EventSender {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EventSenderImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendToTopic(Event event, String topicName) {
        kafkaTemplate.send(topicName, event);
    }
}
