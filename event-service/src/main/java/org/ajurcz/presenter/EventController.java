package org.ajurcz.presenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ajurcz.core.domain.CommentDto;
import org.ajurcz.core.domain.CommentVerifiedDto;
import org.ajurcz.core.domain.Event;
import org.ajurcz.core.domain.PostDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EventController implements EventResource{

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public EventController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<Void> createEvent(Event event) {
        HttpEntity<Event> request = new HttpEntity<>(event);
        try {
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
        catch (ClassNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
