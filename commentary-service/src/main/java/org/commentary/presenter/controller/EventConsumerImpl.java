package org.commentary.presenter.controller;

import org.ajurcz.event.domain.Event;
import org.commentary.core.usecase.HandleEventUseCase;
import org.springframework.stereotype.Component;

@Component
public class EventConsumerImpl implements EventConsumer{
    private final HandleEventUseCase handleEventUseCase;

    public EventConsumerImpl(HandleEventUseCase handleEventUseCase) {
        this.handleEventUseCase = handleEventUseCase;
    }

    @Override
    public void badCommentsListener(Event event) {
        handleEventUseCase.execute(new HandleEventUseCase.Input(event));
    }
}
