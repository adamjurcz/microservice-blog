package org.ajurcz.core.service;

public interface EventSender <T> {
    void sendEvent(T dto);
}
