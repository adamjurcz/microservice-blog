package org.ajurcz.core.domain;

import java.util.List;
import java.util.Objects;

public class Post{
    private final Integer id;
    private final String creatorName;
    private final String content;

    public Post(Integer id, String creatorName, String content){
        Objects.requireNonNull(id);

        this.id = id;
        this.creatorName = creatorName;
        this.content = content;
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
}