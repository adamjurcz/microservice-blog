package org.verify.presenter.controller;

import org.event.core.domain.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(VerifyCommentResource.BASE_URL)
public interface VerifyCommentResource {
    String BASE_URL = "/api/v1/verifycomments";

    @PostMapping
    ResponseEntity<Void> verifyComment(@RequestBody Event event);
}
