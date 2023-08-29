package org.query.configuration;

import org.query.core.service.PostRepository;
import org.query.core.usecase.GetAllPostsUseCase;
import org.query.core.usecase.HandleCommentEventUseCase;
import org.query.core.usecase.HandlePostEventUseCase;
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

}
