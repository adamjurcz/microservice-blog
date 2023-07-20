package org.query.core.service;

import org.query.core.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    void savePost(Integer id, String creatorName, String content);

    Optional<Post> getPost(Integer id);

    List<Post> getAllPosts();
}
