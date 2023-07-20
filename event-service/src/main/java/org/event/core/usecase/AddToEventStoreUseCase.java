package org.event.core.usecase;

import lombok.Value;
import org.event.core.domain.Event;
import org.event.core.service.EventStore;

public class AddToEventStoreUseCase extends UseCase<AddToEventStoreUseCase.Input, AddToEventStoreUseCase.Output>{

    private final EventStore eventStore;

    public AddToEventStoreUseCase(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public Output execute(Input input) {
        eventStore.persistEvent(input.event);
        return new Output();
    }

    @Value
    public static class Input implements UseCase.Input{
        Event event;
    }

    @Value
    public static class Output implements UseCase.Output{
    }
}
