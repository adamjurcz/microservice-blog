package org.ajurcz.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ajurcz.core.service.EventStore;
import org.ajurcz.core.service.PostRepository;
import org.ajurcz.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module {
    @Bean
    HandlePostEventUseCase handlePostEventUseCase(PostRepository postRepository){
        return new HandlePostEventUseCase(postRepository);
    }

    @Bean
    HandleCommentEventUseCase handleCommentEventUseCase(PostRepository postRepository){
        return new HandleCommentEventUseCase(postRepository);
    }

    @Bean
    GetAllPostsUseCase getAllPostsUseCase(PostRepository postRepository){
        return new GetAllPostsUseCase(postRepository);
    }

    @Bean
    HandleEventUseCase handleEventUseCase(ObjectMapper objectMapper,
                                          HandlePostEventUseCase handlePostEventUseCase,
                                          HandleCommentEventUseCase handleCommentEventUseCase){
        return new HandleEventUseCase(objectMapper, handlePostEventUseCase, handleCommentEventUseCase);
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
