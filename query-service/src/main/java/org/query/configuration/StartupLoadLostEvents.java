package org.query.configuration;

import org.ajurcz.event.domain.Event;
import org.ajurcz.event.domain.EventVisitor;
import org.ajurcz.event.response.GetFromEventStoreResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class StartupLoadLostEvents implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${event.url}")
    private String eventUrl;

    private final RestTemplate restTemplate;

    private final EventVisitor eventVisitor;

    public StartupLoadLostEvents(RestTemplate restTemplate, EventVisitor eventVisitor) {
        this.restTemplate = restTemplate;
        this.eventVisitor = eventVisitor;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        GetFromEventStoreResponse eventStoreResponse = getEventsFromEventListener();
        if(eventStoreResponse==null){
            return;
        }

        List<Event> events = eventStoreResponse.events();
        for(Event event_ : events){
            event_.accept(eventVisitor);
        }
    }

    private GetFromEventStoreResponse getEventsFromEventListener(){
        try {
            return restTemplate.getForObject(eventUrl,
                    GetFromEventStoreResponse.class);
        }
        catch (ResourceAccessException exception){
            return null;
        }
    }
}
