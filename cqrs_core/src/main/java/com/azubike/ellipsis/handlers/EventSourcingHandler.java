package com.azubike.ellipsis.handlers;

import com.azubike.ellipsis.domain.AggregateRoot;

/**
 * this sits between the CommandHandler and the EventStore
 * it providers an interface where the commandHandler can obtain the latest state of the aggregate
 * and persists the uncommited changes to the aggregate as events to the eventStoreN
 */
public interface EventSourcingHandler <T>{
     void save(AggregateRoot aggregate);
     T getById(String id);


}
