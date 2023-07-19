package org.ajurcz.presenter.response;

import org.ajurcz.core.domain.Event;

import java.util.List;

public record GetFromEventStoreResponse(List<Event> events) {
}
