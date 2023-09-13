package org.commentary.core.domain;
import java.util.Objects;

public class Commentary{
    private final Integer id;
    private final String content;
    private final Integer postId;
    private final boolean isValid;

    public Commentary(Integer id, String content, Integer postId, boolean isValid){
        Objects.requireNonNull(id);
        Objects.requireNonNull(postId);

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

}