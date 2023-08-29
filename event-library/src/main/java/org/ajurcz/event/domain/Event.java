package org.ajurcz.event.domain;

public interface Event {
    void accept(EventVisitor eventVisitor);
}
