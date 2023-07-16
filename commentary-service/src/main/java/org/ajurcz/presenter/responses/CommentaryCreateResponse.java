package org.ajurcz.presenter.responses;

import org.ajurcz.core.domain.Commentary;

public record CommentaryCreateResponse(Integer id,
                                       String content,
                                       Integer postId) {
}
