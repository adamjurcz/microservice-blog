package org.query.core.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import org.event.core.domain.CommentVerifiedDto;
import org.event.core.domain.Event;
import org.event.core.domain.PostDto;

public class HandleEventUseCase extends UseCase<HandleEventUseCase.Input, HandleEventUseCase.Output> {

    private final ObjectMapper objectMapper;

    private final HandlePostEventUseCase handlePostEventUseCase;

    private final HandleCommentEventUseCase handleCommentEventUseCase;


    public HandleEventUseCase(ObjectMapper objectMapper, HandlePostEventUseCase handlePostEventUseCase, HandleCommentEventUseCase handleCommentEventUseCase) {
        this.objectMapper = objectMapper;
        this.handlePostEventUseCase = handlePostEventUseCase;
        this.handleCommentEventUseCase = handleCommentEventUseCase;
    }

    @Override
    public Output execute(Input input) {
        Event event = input.event;
        boolean success = true;
        try{
            Class<?> clazz = Class.forName(event.getClassName());
            Object object = objectMapper.convertValue(event.getObject(), clazz);
            if(object instanceof PostDto postDto){
                handlePostEventUseCase.execute(new HandlePostEventUseCase.Input(postDto.getId(),
                        postDto.getCreatorName(), postDto.getContent()));
            }
            if(object instanceof CommentVerifiedDto commentDto){
                handleCommentEventUseCase.execute(new HandleCommentEventUseCase.Input(commentDto.getId(),
                        commentDto.getContent(), commentDto.getPostId(), commentDto.isValid()));
            }
        }
        catch (ClassNotFoundException e){
            return new Output(!success);
        }
        return new Output(success);
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
