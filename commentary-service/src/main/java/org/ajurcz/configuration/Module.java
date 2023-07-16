package org.ajurcz.configuration;

import org.ajurcz.core.domain.CommentDto;
import org.ajurcz.core.service.CommentaryRepository;
import org.ajurcz.core.service.EventSender;
import org.ajurcz.core.usecase.CreateCommentaryUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module {
    @Bean
    public CreateCommentaryUseCase createCommentaryUseCase(EventSender<CommentDto> eventSender, CommentaryRepository commentaryRepository){
        return new CreateCommentaryUseCase(eventSender, commentaryRepository);
    }
}
