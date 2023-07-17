package org.ajurcz.core.service;

import org.ajurcz.core.domain.Commentary;

import java.util.List;

public interface CommentaryRepository {

    Commentary persistComment(String content, Integer postId);
}
