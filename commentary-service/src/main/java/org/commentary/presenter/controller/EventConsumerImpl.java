package org.commentary.presenter.controller;

import org.ajurcz.event.domain.CommentVerifiedDto;
import org.commentary.core.usecase.UpdateCommentaryUseCase;
import org.springframework.stereotype.Component;

@Component
public class EventConsumerImpl implements EventConsumer{
    private final UpdateCommentaryUseCase updateCommentaryUseCase;

    public EventConsumerImpl(UpdateCommentaryUseCase updateCommentaryUseCase) {
        this.updateCommentaryUseCase = updateCommentaryUseCase;
    }

    @Override
    public void badCommentsListener(CommentVerifiedDto commentVerifiedDto) {
        if(!commentVerifiedDto.isValid()){
            updateCommentaryUseCase.execute(new UpdateCommentaryUseCase.Input(commentVerifiedDto.getId(), commentVerifiedDto.getContent(),
                    commentVerifiedDto.getPostId(), commentVerifiedDto.isValid()));
        }
    }
}
