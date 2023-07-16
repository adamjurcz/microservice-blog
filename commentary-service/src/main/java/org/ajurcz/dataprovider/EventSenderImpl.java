package org.ajurcz.dataprovider;

import org.ajurcz.core.domain.Event;
import org.ajurcz.core.service.EventSender;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EventSenderImpl <T> implements EventSender <T> {
    private final RestTemplate restTemplate;

    public EventSenderImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean sendEvent(T dto) {
        Event event = new Event(dto.getClass().getName(), dto);
        HttpEntity<Event> request = new HttpEntity<>(event);
        String orchestratorUrl = "http://localhost:8083/api/v1/events";
        ResponseEntity<Void> response =  restTemplate
                .exchange(orchestratorUrl, HttpMethod.POST, request, Void.class);
        return response.getStatusCode() == HttpStatus.OK;
    }
}
