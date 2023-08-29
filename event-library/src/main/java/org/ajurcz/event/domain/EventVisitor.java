package org.ajurcz.event.domain;

public interface EventVisitor {
    void visitCommentDto(CommentDto commentDto);

    void visitCommentVerifiedDto(CommentVerifiedDto commentVerifiedDto);

    void visitPostDto(PostDto postDto);
}
