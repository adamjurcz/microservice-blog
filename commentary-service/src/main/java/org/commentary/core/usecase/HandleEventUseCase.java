package org.commentary.core.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import org.ajurcz.event.domain.Event;
import org.ajurcz.event.domain.CommentVerifiedDto;

public class HandleEventUseCase extends UseCase<HandleEventUseCase.Input, HandleEventUseCase.Output>{

    private final ObjectMapper objectMapper;

    private final UpdateCommentaryUseCase updateCommentaryUseCase;

    public HandleEventUseCase(ObjectMapper objectMapper, UpdateCommentaryUseCase updateCommentaryUseCase) {
        this.objectMapper = objectMapper;
        this.updateCommentaryUseCase = updateCommentaryUseCase;
    }

    @Override
    public Output execute(Input input) {
        Event event = input.event;
        boolean success = true;
        try {
            Class<?> clazz = Class.forName(event.getClassName());
            Object object = objectMapper.convertValue(event.getObject(), clazz);
            if (object instanceof CommentVerifiedDto commentVerifiedDto) {
                if(!commentVerifiedDto.isValid()){
                    updateCommentaryUseCase.execute(new UpdateCommentaryUseCase.Input(commentVerifiedDto.getId(),
                            commentVerifiedDto.getContent(),
                            commentVerifiedDto.getPostId(),
                            commentVerifiedDto.isValid()));
                }
            }
        }
        catch(ClassNotFoundException e){
            return new Output(success);
        }
        return new Output(!success);
    }

    @Value
    public static class Input implements UseCase.Input{
        Event event;
    }

    @Value
    public static class Output implements UseCase.Output{
        boolean isSuccessful;
    }
}
