package org.commentary.core.service;

public interface EventSender <T> {
    void sendEvent(T dto);
}
