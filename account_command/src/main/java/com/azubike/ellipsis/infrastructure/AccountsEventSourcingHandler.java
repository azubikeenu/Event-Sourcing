package com.azubike.ellipsis.infrastructure;

import com.azubike.ellipsis.domain.AccountAggregate;
import com.azubike.ellipsis.domain.AggregateRoot;
import com.azubike.ellipsis.events.BaseEvent;
import com.azubike.ellipsis.handlers.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Comparator;


@Service
@RequiredArgsConstructor
public class AccountsEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {
    private final EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregate) {
        // this saves all the uncommittedChanges to the eventStore
      eventStore.saveEvent(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
      // clear the changes list
      aggregate.makeChangesAsCommited();
    }

    @Override
    public AccountAggregate getById(String id) {
      AccountAggregate aggregate = new AccountAggregate();
      var events = eventStore.getEvents(id);
      if(events != null && !events.isEmpty()){
          // replay the events to get the latest state of the aggregate class
          aggregate.replayEvent(events);
          var latestVersion = events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder());
          aggregate.setVersion(latestVersion.get());
      }
      return aggregate;

    }
}
