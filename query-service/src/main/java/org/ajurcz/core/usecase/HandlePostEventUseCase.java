package org.ajurcz.core.usecase;

import lombok.Value;
import org.ajurcz.core.service.PostRepository;

public class HandlePostEventUseCase extends UseCase<HandlePostEventUseCase.Input, HandlePostEventUseCase.Output>{

    private PostRepository postRepository;

    public HandlePostEventUseCase(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Output execute(Input input) {
        postRepository.savePost(input.id, input.creatorName, input.content);

        return new Output();
    }

    @Value
    public static class Input implements UseCase.Input{
        Integer id;
        String creatorName;
        String content;
    }

    @Value
    public static class Output implements UseCase.Output{
    }
}
