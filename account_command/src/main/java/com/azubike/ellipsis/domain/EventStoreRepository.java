package com.azubike.ellipsis.domain;

import com.azubike.ellipsis.events.EventModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventStoreRepository extends MongoRepository<EventModel, String> {
    // allows you to retrieve events for a given aggregate to replay the event store
    List<EventModel> findEventModelByAggregateIdentifier(String aggregateIdentifier);

}
