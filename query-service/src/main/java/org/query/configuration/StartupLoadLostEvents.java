package org.query.configuration;

import org.event.core.domain.Event;
import org.query.core.usecase.HandleEventUseCase;
import org.event.presenter.response.GetFromEventStoreResponse;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class StartupLoadLostEvents implements ApplicationListener<ContextRefreshedEvent> {
    private final RestTemplate restTemplate;

    private final HandleEventUseCase handleEventUseCase;

    public StartupLoadLostEvents(RestTemplate restTemplate, HandleEventUseCase handleEventUseCase) {
        this.restTemplate = restTemplate;
        this.handleEventUseCase = handleEventUseCase;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        GetFromEventStoreResponse eventStoreResponse = getEventsFromEventListener();
        if(eventStoreResponse==null){
            return;
        }

        List<Event> events = eventStoreResponse.events();
        for(Event event_ : events){
            handleEventUseCase.execute(new HandleEventUseCase.Input(event_));
        }
    }

    private GetFromEventStoreResponse getEventsFromEventListener(){
        return restTemplate.getForObject("http://localhost:8083/api/v1/events",
                GetFromEventStoreResponse.class);
    }
}
