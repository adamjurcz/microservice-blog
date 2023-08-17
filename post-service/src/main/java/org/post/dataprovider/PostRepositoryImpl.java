package org.post.dataprovider;

import org.post.core.domain.Post;
import org.post.core.service.PostRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final PostgresPostRepository postRepository;

    public PostRepositoryImpl(PostgresPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post persistPost(String creatorName, String content) {
        PostData postData = new PostData();
        postData.setCreatorName(creatorName);
        postData.setContent(content);
        return postRepository
                .save(postData)
                .fromThis();
    }
}
