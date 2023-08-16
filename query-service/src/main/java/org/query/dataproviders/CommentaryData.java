package org.query.dataproviders;

import org.query.core.domain.Commentary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CommentaryData {
    @Id
    private final Integer id;
    private final String content;
    private final Integer postId;
    private final boolean isValid;

    public CommentaryData(Integer id, String content, Integer postId, boolean isValid) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.isValid = isValid;
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Integer getPostId() {
        return postId;
    }

    public boolean isValid() {
        return isValid;
    }

    public Commentary fromThis(){
        return new Commentary(this.id, this.content, this.postId, this.isValid);
    }
}
