package org.post.presenter.responses;

public record PostCreateResponse(Integer id,
                                 String creatorName,
                                 String content) {
}
