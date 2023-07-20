package org.query.core.usecase;

import lombok.Value;
import org.query.core.domain.Commentary;
import org.query.core.domain.Post;
import org.query.core.service.PostRepository;


import java.util.Optional;

public class HandleCommentEventUseCase extends UseCase<HandleCommentEventUseCase.Input, HandleCommentEventUseCase.Output> {

    private final PostRepository postRepository;

    public HandleCommentEventUseCase(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Output execute(Input input) {
        Optional<Post> post = postRepository.getPost(input.postId);
        post.ifPresent(value -> value.addCommentary(new Commentary(input.id, input.content, input.postId, input.isValid)));

        return new Output();
    }

    @Value
    public static class Input implements UseCase.Input{
        Integer id;
        String content;
        Integer postId;
        boolean isValid;
    }

    @Value
    public static class Output implements UseCase.Output{
    }
}
