package org.ajurcz.event.domain;

public class CommentVerifiedDto {
    private Integer id;
    private String content;
    private Integer postId;
    private boolean isValid;

    public CommentVerifiedDto(Integer id, String content, Integer postId, boolean isValid) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.isValid = isValid;
    }

    public CommentVerifiedDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
