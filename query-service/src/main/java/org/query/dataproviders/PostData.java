package org.query.dataproviders;

import org.query.core.domain.Commentary;
import org.query.core.domain.Post;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class PostData {
    private final Integer id;
    private final String creatorName;
    private final String content;
    private List<CommentaryData> commentaries;

    public PostData(Integer id, String creatorName, String content){
        this.id = id;
        this.creatorName = creatorName;
        this.content = content;
        this.commentaries = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getContent() {
        return content;
    }

    public List<CommentaryData> getCommentaries(){return commentaries;}

    public Post fromThis() {
        List<Commentary> mappedCommentaries = this.getCommentaries()
                .stream()
                .map(CommentaryData::fromThis)
                .toList();

        Post post = new Post(this.id, this.creatorName, this.content);
        post.getCommentaries().addAll(mappedCommentaries);

        return post;
    }
}
