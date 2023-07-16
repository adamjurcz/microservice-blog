package org.ajurcz.core.service;

import org.ajurcz.core.domain.Post;

public interface PostRepository {
    Post persistPost(String creatorName, String content);
}
