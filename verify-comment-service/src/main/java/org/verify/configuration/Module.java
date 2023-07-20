package org.verify.configuration;

import org.verify.core.usecase.VerifyCommentUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module {
    @Bean
    VerifyCommentUseCase verifyCommentUseCase(){
        return new VerifyCommentUseCase();
    }
}
