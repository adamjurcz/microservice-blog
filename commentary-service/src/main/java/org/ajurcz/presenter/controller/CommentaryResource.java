package org.ajurcz.presenter.controller;

import org.ajurcz.presenter.requests.CommentaryRequest;

import org.ajurcz.presenter.responses.CommentaryCreateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CommentaryResource.BASE_URL)
public interface CommentaryResource {
    String BASE_URL = "/api/v1/commentary";

    @PostMapping("/{postId}")
    ResponseEntity<CommentaryCreateResponse> createCommentary(@PathVariable Integer postId,
                                                              @RequestBody CommentaryRequest commentaryRequest);
}
