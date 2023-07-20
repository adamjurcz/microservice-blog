package org.event.core.service;

import org.event.core.domain.Event;

import java.util.List;

public interface EventStore {
    Event persistEvent(Event event);
    List<Event> getAllEvents();
}
