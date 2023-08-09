package org.commentary.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.commentary.core.service.CommentaryRepository;

import org.commentary.core.usecase.CreateCommentaryUseCase;
import org.commentary.core.usecase.HandleEventUseCase;
import org.commentary.core.usecase.UpdateCommentaryUseCase;
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
}
