package org.commentary.presenter.controller;

import org.ajurcz.event.domain.Event;
import org.commentary.presenter.requests.CommentaryRequest;
import org.commentary.presenter.responses.CommentaryCreateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CommentaryResource.BASE_URL)
public interface CommentaryResource {
    String BASE_URL = "/api/v1/commentary";

    @PostMapping("/{postId}")
    ResponseEntity<CommentaryCreateResponse> createCommentary(@PathVariable Integer postId,
                                                              @RequestBody CommentaryRequest commentaryRequest);

    @PostMapping
    ResponseEntity<Void> eventHandler(@RequestBody Event event);
}
