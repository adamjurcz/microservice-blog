package org.ajurcz.dataproviders;

import org.ajurcz.core.domain.Event;
import org.ajurcz.core.service.EventStore;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class EventStoreImpl implements EventStore{
    private List<Event> events;

    public EventStoreImpl(List<Event> events) {
        this.events = new ArrayList<>();
    }

    @Override
    public Event persistEvent(Event event) {
        events.add(event);
        return event;
    }

    @Override
    public List<Event> getAllEvents() {
        return events;
    }
}

