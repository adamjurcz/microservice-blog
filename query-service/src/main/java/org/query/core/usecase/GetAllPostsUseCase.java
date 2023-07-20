package org.query.core.usecase;

import lombok.Value;
import org.query.core.domain.Post;
import org.query.core.service.PostRepository;


import java.util.List;

public class GetAllPostsUseCase extends UseCase<GetAllPostsUseCase.Input, GetAllPostsUseCase.Output> {

    private final PostRepository postRepository;

    public GetAllPostsUseCase(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Output execute(Input input) {
        return new Output(postRepository.getAllPosts());
    }

    @Value
    public static class Input implements UseCase.Input{
    }

    @Value
    public static class Output implements UseCase.Output{
        List<Post> posts;
    }
}
