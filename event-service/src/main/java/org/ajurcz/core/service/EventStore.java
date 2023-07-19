package org.ajurcz.core.service;

import org.ajurcz.core.domain.Event;

import java.util.List;

public interface EventStore {
    Event persistEvent(Event event);
    List<Event> getAllEvents();
}
