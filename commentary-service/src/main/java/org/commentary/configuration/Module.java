package org.commentary.configuration;

import org.commentary.core.service.CommentaryRepository;

import org.commentary.core.usecase.CreateCommentaryUseCase;
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

}
