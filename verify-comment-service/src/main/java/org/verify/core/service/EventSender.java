package org.verify.core.service;

public interface EventSender <T>{
    void sendEvent(T dto);
}
