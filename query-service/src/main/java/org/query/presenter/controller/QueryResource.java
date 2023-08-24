package org.query.presenter.controller;

import org.ajurcz.event.domain.Event;
import org.query.presenter.response.PostGetAllResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(QueryResource.BASE_URL)
public interface QueryResource {
    String BASE_URL = "/api/v1/queries";

    @GetMapping("/posts")
    ResponseEntity<PostGetAllResponse> getAllPosts();
}
