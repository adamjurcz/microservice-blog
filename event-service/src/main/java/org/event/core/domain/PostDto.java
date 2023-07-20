package org.event.core.domain;

public class PostDto {
    private Integer id;
    private String creatorName;
    private String content;

    public PostDto() {
    }

    public PostDto(Integer id, String creatorName, String content) {
        this.id = id;
        this.creatorName = creatorName;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
