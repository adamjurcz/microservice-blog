package org.event.presenter.response;

import org.event.core.domain.Event;

import java.util.List;

public record GetFromEventStoreResponse(List<Event> events) {
}
