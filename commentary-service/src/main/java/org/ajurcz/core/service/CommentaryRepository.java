package org.ajurcz.core.service;

import org.ajurcz.core.domain.Commentary;

import java.util.List;

public interface CommentaryRepository {
    List<Commentary> getAllCommentaries();

    Commentary persistComment(String content, Integer postId);
}
