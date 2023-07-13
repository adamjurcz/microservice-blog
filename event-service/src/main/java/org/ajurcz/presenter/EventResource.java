package org.ajurcz.presenter;

import org.ajurcz.core.domain.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EventResource.BASE_URL)
public interface EventResource {
    String BASE_URL = "/api/v1/events";

    @PostMapping
    ResponseEntity<Void> createEvent(@RequestBody Event event);
}
