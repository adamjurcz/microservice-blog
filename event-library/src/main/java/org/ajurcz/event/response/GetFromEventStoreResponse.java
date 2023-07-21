package org.ajurcz.event.response;

import org.ajurcz.event.domain.Event;

import java.util.List;

public record GetFromEventStoreResponse(List<Event> events) {
}
