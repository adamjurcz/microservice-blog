package org.ajurcz.dataproviders;

import org.ajurcz.core.domain.Post;
import org.ajurcz.core.service.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {
    List<Post> posts;

    public PostRepositoryImpl() {
        this.posts = new ArrayList<>();
    }

    @Override
    public void savePost(Integer id, String creatorName, String content) {
        posts.add(new Post(id, creatorName, content));
    }

    @Override
    public Optional<Post> getPost(Integer id) {
        return posts.stream().filter(post_ -> post_.getId().equals(id)).findFirst();
    }

    @Override
    public List<Post> getAllPosts() {
        return posts;
    }
}
