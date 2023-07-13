package org.ajurcz.presenter;

import org.ajurcz.core.domain.Event;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EventController implements EventResource{

    private final RestTemplate restTemplate;

    public EventController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<Void> createEvent(Event event) {
        HttpEntity<Event> request = new HttpEntity<>(event);
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:8084/api/v1/queries", HttpMethod.POST, request, Void.class);
        if(response.getStatusCode() == HttpStatus.OK){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
