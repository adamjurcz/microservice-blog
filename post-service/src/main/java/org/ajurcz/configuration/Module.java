package org.ajurcz.configuration;

import org.ajurcz.core.domain.PostDto;
import org.ajurcz.core.service.EventSender;
import org.ajurcz.core.service.PostRepository;
import org.ajurcz.core.usecase.CreatePostUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module {
    @Bean
    CreatePostUseCase createPostUseCase(EventSender<PostDto>eventSender, PostRepository postRepository){
        return new CreatePostUseCase(eventSender, postRepository);
    }

}
