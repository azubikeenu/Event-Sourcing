package com.azubike.ellipsis.infrastructure;

import com.azubike.ellipsis.events.BaseEvent;

import java.util.List;

/**
 * This provides an interface abstraction for accessing the EventStore Business logic
 */
public interface EventStore {
    void saveEvent(String aggregateId , Iterable<BaseEvent> events , int version);
    List<BaseEvent> getEvents(String aggregateId);
}
