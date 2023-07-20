package org.query.presenter.response;

import org.query.core.domain.Post;

import java.util.List;

public record PostGetAllResponse(List<Post> posts) {
}
