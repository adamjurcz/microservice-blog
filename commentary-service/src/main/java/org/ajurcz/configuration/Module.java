package org.ajurcz.configuration;

import org.ajurcz.core.service.CommentaryRepository;
import org.ajurcz.core.usecase.CreateCommentaryUseCase;
import org.ajurcz.core.usecase.UpdateCommentaryUseCase;
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
