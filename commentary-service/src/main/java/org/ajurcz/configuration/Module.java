package org.ajurcz.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ajurcz.core.service.CommentaryRepository;
import org.ajurcz.core.service.EventStore;
import org.ajurcz.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module {
    @Bean
    public CreateCommentaryUseCase createCommentaryUseCase(CommentaryRepository commentaryRepository){
        return new CreateCommentaryUseCase(commentaryRepository);
    }

    @Bean
    public UpdateCommentaryUseCase updateCommentaryUseCase(CommentaryRepository commentaryRepository){
        return new UpdateCommentaryUseCase(commentaryRepository);
    }

    @Bean
    public HandleEventUseCase handleEventUseCase(ObjectMapper objectMapper, UpdateCommentaryUseCase updateCommentaryUseCase){
        return new HandleEventUseCase(objectMapper, updateCommentaryUseCase);
    }

    @Bean
    public AddToEventStoreUseCase addToEventStoreUseCase(EventStore eventStore){
        return new AddToEventStoreUseCase(eventStore);
    }

    @Bean
    public GetEventsFromEventStoreUseCase getEventsFromEventStoreUseCase(EventStore eventStore){
        return new GetEventsFromEventStoreUseCase(eventStore);
    }
}
