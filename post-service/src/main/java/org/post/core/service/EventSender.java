package org.post.core.service;

public interface EventSender <T> {
    void sendEvent(T dto);
}
