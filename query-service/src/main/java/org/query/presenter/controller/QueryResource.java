package org.query.presenter.controller;

import org.event.core.domain.Event;
import org.query.presenter.response.PostGetAllResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(QueryResource.BASE_URL)
public interface QueryResource {
    String BASE_URL = "/api/v1/queries";

    @PostMapping
    ResponseEntity<Void> eventHandler(@RequestBody Event event);

    @GetMapping("/posts")
    ResponseEntity<PostGetAllResponse> getAllPosts();
}