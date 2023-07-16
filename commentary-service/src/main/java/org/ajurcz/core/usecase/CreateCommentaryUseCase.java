package org.ajurcz.core.usecase;

import lombok.Value;
import org.ajurcz.core.domain.CommentDto;
import org.ajurcz.core.domain.Commentary;
import org.ajurcz.core.service.CommentaryRepository;
import org.ajurcz.core.service.EventSender;

public class CreateCommentaryUseCase extends UseCase<CreateCommentaryUseCase.Input, CreateCommentaryUseCase.Output>{
    private EventSender<CommentDto> eventSender;

    private CommentaryRepository commentaryRepository;

    public CreateCommentaryUseCase(EventSender<CommentDto> eventSender, CommentaryRepository commentaryRepository) {
        this.eventSender = eventSender;
        this.commentaryRepository = commentaryRepository;
    }

    @Override
    public Output execute(Input input) {
        Commentary commentary = commentaryRepository.persistComment(input.content, input.postId);

        eventSender.sendEvent(new CommentDto(commentary.getId(), commentary.getContent(), commentary.getPostId()));
        return new Output(commentary);
    }

    @Value
    public static class Input implements UseCase.Input{
        String content;
        Integer postId;
    }

    @Value
    public static class Output implements UseCase.Output{
        Commentary commentary;
    }
}
