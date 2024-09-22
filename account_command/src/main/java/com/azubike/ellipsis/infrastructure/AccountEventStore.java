
/*
    Optimistic Concurrency Control!
    Optimistic Concurrency Control (OCC) is a strategy used to manage concurrent updates to shared data,
    ensuring data integrity and consistency.
    Key principles:
    1. Optimism: Assume that multiple transactions can execute concurrently without conflicts.
    2. Versioning: Use version numbers or timestamps to track changes.
    3. Validation: Verify that the data hasn't changed since the transaction started.
 **/

package com.azubike.ellipsis.infrastructure;

import com.azubike.ellipsis.domain.AccountAggregate;
import com.azubike.ellipsis.domain.EventStoreRepository;
import com.azubike.ellipsis.events.BaseEvent;
import com.azubike.ellipsis.events.EventModel;
import com.azubike.ellipsis.exceptions.AggregateNotFoundException;
import com.azubike.ellipsis.exceptions.ConcurrencyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/*
* This is an implementation of the EventStore interface defined in the core module, it is responsible for '
* the persistence of EventData ie aggregate objects and also their versioning
* */
@Service
@RequiredArgsConstructor
public class AccountEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;
    private final AccountEventProducer accountEventProducer;

    @Override
    public void saveEvent(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        // first of all we check if the aggregate is already persisted in the database and option the event stream
        List<EventModel> eventStream = eventStoreRepository.findEventModelByAggregateIdentifier(aggregateId);
        // perform concurrency control
          // the default value for the version for a new aggregate is -1
           // Here we must ensure that only one instance of aggregate object can be mutated when accessed by multiple threads
           // we do this by checking expectedVersion [which is the passed aggregate object's version on save] wth the last updated version
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        var version = expectedVersion;
        for (BaseEvent event : events) {
            // increment the version
            version++;
            event.setVersion(version);
            // Create the EventModel from the aggregate object
            EventModel eventModel = EventModel.builder().eventData(event).version(version).timestamp(new Date()).
                    aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getSimpleName())
                    .eventType(event.getClass().getSimpleName())
                    .build();
            // Persist the EventModel to the EventStore
            EventModel savedModel = eventStoreRepository.save(eventModel);
            if (!savedModel.getId().isEmpty()) {
                // send event to kafka
                accountEventProducer.produce(event.getClass().getSimpleName() , event);
            }
        }

    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        List<EventModel> eventStream = eventStoreRepository.findEventModelByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException(String.format("incorrect aggregate id %s supplied", aggregateId));
        }
        return eventStream.stream().map(EventModel::getEventData).toList();
    }
}
