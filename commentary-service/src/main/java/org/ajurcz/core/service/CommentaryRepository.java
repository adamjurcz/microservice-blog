package org.ajurcz.core.service;

import org.ajurcz.core.domain.Commentary;

public interface CommentaryRepository {

    Commentary persistComment(String content, Integer postId, boolean isValid);

    Commentary updateComment(Integer id, String content, Integer postId, boolean isValid);
}
