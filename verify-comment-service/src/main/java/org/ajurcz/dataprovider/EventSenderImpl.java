package org.ajurcz.dataprovider;

import org.ajurcz.core.domain.Event;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.ajurcz.core.service.EventSender;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EventSenderImpl <T> implements EventSender <T> {
    private final RestTemplate restTemplate;

    public EventSenderImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendEvent(T dto) {
        Event event = new Event(dto.getClass().getName(), dto);
        HttpEntity<Event> request = new HttpEntity<>(event);
        String orchestratorUrl = "http://localhost:8083/api/v1/events";
        restTemplate
                .exchange(orchestratorUrl, HttpMethod.POST, request, Void.class);
    }
}
