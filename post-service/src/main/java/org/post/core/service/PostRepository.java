package org.post.core.service;

import org.post.core.domain.Post;

public interface PostRepository {
    Post persistPost(String creatorName, String content);
}
