package org.event.dataproviders;

import org.event.core.domain.Event;
import org.event.core.service.EventStore;
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

