package org.ajurcz.core.usecase;

import lombok.Value;
import org.ajurcz.core.domain.Event;
import org.ajurcz.core.service.EventStore;

import java.util.List;

public class GetEventsFromEventStoreUseCase extends UseCase<GetEventsFromEventStoreUseCase.Input, GetEventsFromEventStoreUseCase.Output>{

    private final EventStore eventStore;

    public GetEventsFromEventStoreUseCase(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public Output execute(Input input) {
        return new Output(eventStore.getAllEvents());
    }

    @Value
    public static class Input implements UseCase.Input{
    }

    @Value
    public static class Output implements UseCase.Output{
        List<Event> events;
    }
}
