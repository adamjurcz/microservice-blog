package org.commentary.presenter.responses;

public record CommentaryCreateResponse(Integer id,
                                       String content,
                                       Integer postId) {
}
