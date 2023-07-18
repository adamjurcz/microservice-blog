package org.ajurcz.dataprovider;

import org.ajurcz.core.domain.Commentary;
import org.ajurcz.core.service.CommentaryRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class CommentaryRepositoryImpl implements CommentaryRepository {
    private List<Commentary> commentaries;

    public CommentaryRepositoryImpl() {
        this.commentaries = new ArrayList<>();
    }

    @Override
    public Commentary persistComment(String content, Integer postId, boolean isValid) {
        Integer newCommentaryId;
        if(!commentaries.isEmpty()){
            newCommentaryId = commentaries.stream().max(Comparator.comparing(Commentary::getId)).get().getId() + 1;
        }
        else{
            newCommentaryId = 0;
        }

        Commentary commentary = new Commentary(newCommentaryId, content, postId, isValid);

        commentaries.add(commentary);
        return commentary;
    }

    @Override
    public Commentary updateComment(Integer id, String content, Integer postId, boolean isValid){
        commentaries.removeIf(object -> object.getId().equals(id));

        Commentary commentary = new Commentary(id, content, postId, isValid);
        commentaries.add(commentary);
        return commentary;
    }

}
