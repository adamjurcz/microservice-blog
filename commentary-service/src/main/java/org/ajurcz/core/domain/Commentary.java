package org.ajurcz.core.domain;
import java.util.Objects;

public class Commentary{
    private final Integer id;
    private final String content;
    private final Integer postId;
    public Commentary(Integer id, String content, Integer postId){
        Objects.requireNonNull(id);
        Objects.requireNonNull(postId);

        this.id = id;
        this.content = content;
        this.postId = postId;
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
}