package org.post.dataprovider;

import org.ajurcz.event.domain.Event;
import org.post.core.service.EventSender;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class EventSenderImpl <T> implements EventSender<T> {
    private final RestTemplate restTemplate;

    public EventSenderImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendEvent(T dto) {
        Event event = new Event(dto.getClass().getName(), dto);
        HttpEntity<Event> request = new HttpEntity<>(event);
        String orchestratorUrl = "http://localhost:8083/api/v1/events";
        try {
            restTemplate
                    .exchange(orchestratorUrl, HttpMethod.POST, request, Void.class);
        }
        catch (ResourceAccessException exception){
            exception.printStackTrace();
        }
    }
}
