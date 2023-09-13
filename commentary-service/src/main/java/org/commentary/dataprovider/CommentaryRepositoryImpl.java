package org.commentary.dataprovider;

import org.commentary.core.domain.Commentary;
import org.commentary.core.service.CommentaryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CommentaryRepositoryImpl implements CommentaryRepository {
    private final PostgresCommentaryRepository postgresCommentaryRepository;

    public CommentaryRepositoryImpl(PostgresCommentaryRepository postgresCommentaryRepository) {
        this.postgresCommentaryRepository = postgresCommentaryRepository;
    }

    @Override
    public Commentary persistComment(String content, Integer postId, boolean isValid) {
        CommentaryData commentaryData = new CommentaryData();
        commentaryData.setContent(content);
        commentaryData.setPostId(postId);
        commentaryData.setValid(isValid);
        return postgresCommentaryRepository
                .save(commentaryData)
                .fromThis();
    }

    @Override
    public Commentary updateComment(Integer id, String content, Integer postId, boolean isValid) {
        CommentaryData commentaryData = new CommentaryData();
        commentaryData.setId(id);
        commentaryData.setContent(content);
        commentaryData.setPostId(postId);
        commentaryData.setValid(isValid);

        return postgresCommentaryRepository
                .save(commentaryData)
                .fromThis();
    }
}
