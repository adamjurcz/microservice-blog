package org.post.core.service;

import org.ajurcz.event.domain.Event;

public interface EventSender {
    void sendToTopic(Event event, String topicName);
}
