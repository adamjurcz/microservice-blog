package org.ajurcz.event.domain;

public class CommentDto implements Event {
    private Integer id;
    private String content;
    private Integer postId;

    public CommentDto(Integer id, String content, Integer postId) {
        this.id = id;
        this.content = content;
        this.postId = postId;
    }

    public CommentDto() {
    }

    @Override
    public void accept(EventVisitor eventVisitor) {
        eventVisitor.visitCommentDto(this);
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
}
