package org.query.presenter.controller;

import org.ajurcz.event.domain.Event;
import org.ajurcz.event.domain.EventVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EventConsumerImpl implements EventConsumer{
    Logger logger = LoggerFactory.getLogger(EventConsumerImpl.class);

    private final EventVisitor eventVisitor;

    public EventConsumerImpl(EventVisitor eventVisitor) {
        this.eventVisitor = eventVisitor;
    }

    @Override
    public void eventListener(Event event) {
        logger.info("[QUERY]Odebralem event" + event.toString());
        event.accept(eventVisitor);
    }
}
