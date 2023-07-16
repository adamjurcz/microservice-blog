package org.ajurcz.core.service;

public interface EventSender <T> {
    boolean sendEvent(T dto);
}
