package org.ajurcz.core.domain;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commentary that = (Commentary) o;
        return isValid == that.isValid && Objects.equals(id, that.id) && Objects.equals(content, that.content) && Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, postId, isValid);
    }
}