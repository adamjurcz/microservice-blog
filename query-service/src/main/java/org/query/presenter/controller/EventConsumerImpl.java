package org.query.presenter.controller;

import org.ajurcz.event.domain.Event;
import org.query.core.usecase.HandleEventUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EventConsumerImpl implements EventConsumer{
    Logger logger = LoggerFactory.getLogger(EventConsumerImpl.class);

    private final HandleEventUseCase handleEventUseCase;

    public EventConsumerImpl(HandleEventUseCase handleEventUseCase) {
        this.handleEventUseCase = handleEventUseCase;
    }

    @Override
    public void eventListener(Event event) {
        logger.info("QUERY-dostalem event");
        handleEventUseCase.execute(new HandleEventUseCase.Input(event));
    }
}
