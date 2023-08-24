package org.event.presenter;

import org.ajurcz.event.domain.Event;
import org.event.core.usecase.GetEventsFromEventStoreUseCase;
import org.ajurcz.event.response.GetFromEventStoreResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventController implements EventResource{

    private final GetEventsFromEventStoreUseCase getEventsFromEventStoreUseCase;


    public EventController(GetEventsFromEventStoreUseCase getEventsFromEventStoreUseCase) {

        this.getEventsFromEventStoreUseCase = getEventsFromEventStoreUseCase;
    }

    @Override
    public ResponseEntity<GetFromEventStoreResponse> getAllEvents(){
        List<Event> events = getEventsFromEventStoreUseCase.execute(new GetEventsFromEventStoreUseCase.Input()).getEvents();
        GetFromEventStoreResponse getFromEventStoreResponse = new GetFromEventStoreResponse(events);
        return new ResponseEntity<>(getFromEventStoreResponse, HttpStatus.OK);
    }
}
