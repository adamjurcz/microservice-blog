package org.query.dataproviders;

import org.query.core.domain.Commentary;
import org.query.core.domain.Post;
import org.query.core.service.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final MongoPostRepository mongoPostRepository;

    public PostRepositoryImpl(MongoPostRepository mongoPostRepository) {
        this.mongoPostRepository = mongoPostRepository;
    }

    @Override
    public void savePost(Integer id, String creatorName, String content) {
        PostData postData = new PostData(id, creatorName, content);
        mongoPostRepository.save(postData);
    }

    @Override
    public void addCommentToPost(Commentary commentary) {
        Optional<PostData> postDataOptional = mongoPostRepository.findById(commentary.getPostId());
        if(postDataOptional.isPresent()){
            PostData postData = postDataOptional.get();
            postData.getCommentaries().add(new CommentaryData(commentary.getId(),
                    commentary.getContent(), commentary.getPostId(), commentary.isValid()));
            mongoPostRepository.save(postData);
        }
    }

    @Override
    public Optional<Post> getPost(Integer id) {
        return mongoPostRepository.findById(id).map(PostData::fromThis);
    }

    @Override
    public List<Post> getAllPosts() {
        return mongoPostRepository.findAll().stream().map(PostData::fromThis).toList();
    }
}
