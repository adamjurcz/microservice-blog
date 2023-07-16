package org.ajurcz.core.usecase;

import lombok.Value;
import org.ajurcz.core.domain.Post;
import org.ajurcz.core.domain.PostDto;
import org.ajurcz.core.service.EventSender;
import org.ajurcz.core.service.PostRepository;

public class CreatePostUseCase extends UseCase<CreatePostUseCase.Input, CreatePostUseCase.Output>{

    private EventSender<PostDto> eventSender;

    private PostRepository postRepository;

    public CreatePostUseCase(EventSender<PostDto> eventSender, PostRepository postRepository) {
        this.eventSender = eventSender;
        this.postRepository = postRepository;
    }

    @Override
    public Output execute(Input input) {
        Post post = postRepository.persistPost(input.creatorName, input.content);

        eventSender.sendEvent(new PostDto(post.getId(), post.getCreatorName(), post.getContent()));
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
