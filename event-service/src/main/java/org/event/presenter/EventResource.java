package org.event.presenter;

import org.event.core.domain.Event;
import org.event.presenter.response.GetFromEventStoreResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EventResource.BASE_URL)
public interface EventResource {
    String BASE_URL = "/api/v1/events";

    @PostMapping
    ResponseEntity<Void> createEvent(@RequestBody Event event);

    @GetMapping
    ResponseEntity<GetFromEventStoreResponse> getAllEvents();

}
