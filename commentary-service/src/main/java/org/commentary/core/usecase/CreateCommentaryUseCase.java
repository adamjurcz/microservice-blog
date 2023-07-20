package org.commentary.core.usecase;

import lombok.Value;
import org.commentary.core.domain.Commentary;
import org.commentary.core.service.CommentaryRepository;

public class CreateCommentaryUseCase extends UseCase<CreateCommentaryUseCase.Input, CreateCommentaryUseCase.Output> {
    private final CommentaryRepository commentaryRepository;

    public CreateCommentaryUseCase(CommentaryRepository commentaryRepository) {
        this.commentaryRepository = commentaryRepository;
    }

    @Override
    public Output execute(Input input) {
        Commentary commentary = commentaryRepository.persistComment(input.content, input.postId, input.isValid);
        return new Output(commentary);
    }

    @Value
    public static class Input implements UseCase.Input {
        String content;
        Integer postId;
        boolean isValid;
    }

    @Value
    public static class Output implements UseCase.Output {
        Commentary commentary;
    }
}
