package org.ajurcz.configuration;

import org.ajurcz.core.service.EventStore;
import org.ajurcz.core.usecase.AddToEventStoreUseCase;
import org.ajurcz.core.usecase.GetEventsFromEventStoreUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module {
    @Bean
    public AddToEventStoreUseCase addToEventStoreUseCase(EventStore eventStore){
        return new AddToEventStoreUseCase(eventStore);
    }

    @Bean
    public GetEventsFromEventStoreUseCase getEventsFromEventStoreUseCase(EventStore eventStore){
        return new GetEventsFromEventStoreUseCase(eventStore);
    }
}
