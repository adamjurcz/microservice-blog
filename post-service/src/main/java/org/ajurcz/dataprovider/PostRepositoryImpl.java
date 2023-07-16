package org.ajurcz.dataprovider;

import org.ajurcz.core.domain.Post;
import org.ajurcz.core.service.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {
    List<Post> posts;

    public PostRepositoryImpl() {
        this.posts = new ArrayList<>();
    }

    @Override
    public Post persistPost(String creatorName, String content) {
        Integer newPostId;
        if(!posts.isEmpty()){
            newPostId = posts.stream().max(Comparator.comparing(Post::getId)).get().getId() + 1;
        }
        else{
            newPostId = 0;
        }
        Post post = new Post(newPostId, creatorName, content);

        posts.add(post);
        return post;
    }
}
