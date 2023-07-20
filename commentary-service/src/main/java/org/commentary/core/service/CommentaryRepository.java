package org.commentary.core.service;

import org.commentary.core.domain.Commentary;

public interface CommentaryRepository {

    Commentary persistComment(String content, Integer postId, boolean isValid);

    Commentary updateComment(Integer id, String content, Integer postId, boolean isValid);
}
