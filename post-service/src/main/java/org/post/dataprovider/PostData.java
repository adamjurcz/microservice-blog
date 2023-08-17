package org.post.dataprovider;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.post.core.domain.Post;

import java.util.Objects;

@Entity
public class PostData{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String creatorName;
    private String content;

    public PostData() {
    }

    public PostData(Integer id, String creatorName, String content){
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostData postData = (PostData) o;
        return Objects.equals(id, postData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Post fromThis() {
        return new Post(this.id, this.creatorName, this.content);
    }
}