package org.post.configuration;

import org.post.core.service.PostRepository;
import org.post.core.usecase.CreatePostUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module {
    @Bean
    CreatePostUseCase createPostUseCase(PostRepository postRepository){
        return new CreatePostUseCase(postRepository);
    }

}
