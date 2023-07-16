package org.ajurcz.core.usecase;

import lombok.Value;
import org.ajurcz.core.domain.Commentary;
import org.ajurcz.core.domain.Post;
import org.ajurcz.core.service.PostRepository;

import java.util.Optional;

public class HandleCommentEventUseCase extends UseCase<HandleCommentEventUseCase.Input, HandleCommentEventUseCase.Output> {

    private PostRepository postRepository;

    public HandleCommentEventUseCase(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Output execute(Input input) {
        Optional<Post> post = postRepository.getPost(input.postId);
        post.ifPresent(value -> value.addCommentary(new Commentary(input.id, input.content, input.postId)));

        return new Output();
    }

    @Value
    public static class Input implements UseCase.Input{
        Integer id;
        String content;
        Integer postId;
    }

    @Value
    public static class Output implements UseCase.Output{
    }
}
