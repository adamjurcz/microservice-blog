package org.event.presenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ajurcz.event.domain.CommentDto;
import org.ajurcz.event.domain.CommentVerifiedDto;
import org.ajurcz.event.domain.Event;
import org.event.core.usecase.AddToEventStoreUseCase;
import org.event.core.usecase.GetEventsFromEventStoreUseCase;
import org.ajurcz.event.response.GetFromEventStoreResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class EventController implements EventResource{

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final AddToEventStoreUseCase addToEventStoreUseCase;

    private final GetEventsFromEventStoreUseCase getEventsFromEventStoreUseCase;


    public EventController(RestTemplate restTemplate, ObjectMapper objectMapper,
                           AddToEventStoreUseCase addToEventStoreUseCase, GetEventsFromEventStoreUseCase getEventsFromEventStoreUseCase) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.addToEventStoreUseCase = addToEventStoreUseCase;
        this.getEventsFromEventStoreUseCase = getEventsFromEventStoreUseCase;
    }

    @Override
    public ResponseEntity<Void> createEvent(Event event) {
        addToEventStoreUseCase.execute(new AddToEventStoreUseCase.Input(event));

        try {
            HttpEntity<Event> request = new HttpEntity<>(event);
            Class<?> clazz = Class.forName(event.getClassName());
            Object object = objectMapper.convertValue(event.getObject(), clazz);
            if (object instanceof CommentDto) {
                restTemplate.exchange("http://localhost:8085/api/v1/verifycomments", HttpMethod.POST, request, Void.class);
            }
            else if (object instanceof CommentVerifiedDto commentVerifiedDto){
                if(commentVerifiedDto.isValid()) {
                    restTemplate.exchange("http://localhost:8084/api/v1/queries", HttpMethod.POST, request, Void.class);
                }
                else{
                    restTemplate.exchange("http://localhost:8082/api/v1/commentary", HttpMethod.POST, request, Void.class);
                }
            }
            else{
                restTemplate.exchange("http://localhost:8084/api/v1/queries", HttpMethod.POST, request, Void.class);
            }
        }
        catch (ClassNotFoundException | ResourceAccessException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetFromEventStoreResponse> getAllEvents(){
        List<Event> events = getEventsFromEventStoreUseCase.execute(new GetEventsFromEventStoreUseCase.Input()).getEvents();
        GetFromEventStoreResponse getFromEventStoreResponse = new GetFromEventStoreResponse(events);
        return new ResponseEntity<>(getFromEventStoreResponse, HttpStatus.OK);
    }
}
