package org.query.presenter.externals;

import org.ajurcz.event.domain.CommentDto;
import org.ajurcz.event.domain.CommentVerifiedDto;
import org.ajurcz.event.domain.EventVisitor;
import org.ajurcz.event.domain.PostDto;
import org.query.core.usecase.HandleCommentEventUseCase;
import org.query.core.usecase.HandlePostEventUseCase;
import org.springframework.stereotype.Component;

@Component
public class EventVisitorImpl implements EventVisitor {
    private final HandlePostEventUseCase handlePostEventUseCase;

    private final HandleCommentEventUseCase handleCommentEventUseCase;

    public EventVisitorImpl(HandlePostEventUseCase handlePostEventUseCase, HandleCommentEventUseCase handleCommentEventUseCase) {
        this.handlePostEventUseCase = handlePostEventUseCase;
        this.handleCommentEventUseCase = handleCommentEventUseCase;
    }

    @Override
    public void visitCommentDto(CommentDto commentDto) {

    }

    @Override
    public void visitCommentVerifiedDto(CommentVerifiedDto commentVerifiedDto) {
        handleCommentEventUseCase.execute(new HandleCommentEventUseCase.Input(commentVerifiedDto.getId(),
                commentVerifiedDto.getContent(), commentVerifiedDto.getPostId(), commentVerifiedDto.isValid()));
    }

    @Override
    public void visitPostDto(PostDto postDto) {
        handlePostEventUseCase.execute(new HandlePostEventUseCase.Input(postDto.getId(),
                postDto.getCreatorName(), postDto.getContent()));
    }
}
