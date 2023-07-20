package org.ajurcz.core.usecase;

import lombok.Value;
import org.ajurcz.core.domain.Post;
import org.ajurcz.core.service.PostRepository;

public class CreatePostUseCase extends UseCase<CreatePostUseCase.Input, CreatePostUseCase.Output>{
    private final PostRepository postRepository;

    public CreatePostUseCase(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Output execute(Input input) {
        Post post = postRepository.persistPost(input.creatorName, input.content);

        return new Output(post);
    }

    @Value
    public static class Input implements UseCase.Input{
        String creatorName;
        String content;
    }

    @Value
    public static class Output implements UseCase.Output{
        Post post;
    }
}
