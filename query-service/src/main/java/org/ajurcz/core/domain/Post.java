package org.ajurcz.core.domain;

import java.util.ArrayList;
import java.util.List;

public class Post{
    private final Integer id;
    private final String creatorName;
    private final String content;
    private List<Commentary> commentaries;

    public Post(Integer id, String creatorName, String content){
        this.id = id;
        this.creatorName = creatorName;
        this.content = content;
        commentaries = new ArrayList<>();
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

    public List<Commentary> getCommentaries(){return commentaries;}

    public void addCommentary(Commentary commentary){
        commentaries.add(commentary);
    }
}