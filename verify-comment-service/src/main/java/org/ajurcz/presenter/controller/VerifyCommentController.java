package org.ajurcz.presenter.controller;

import org.ajurcz.core.domain.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class VerifyCommentController implements VerifyCommentResource{

    @Override
    public ResponseEntity<Void> verifyComment(Event event) {
        return null;
    }
}
