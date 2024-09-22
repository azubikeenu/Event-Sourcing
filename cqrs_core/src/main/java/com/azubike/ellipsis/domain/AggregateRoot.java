package com.azubike.ellipsis.domain;

import com.azubike.ellipsis.events.BaseEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Setter
@Getter
public class AggregateRoot {
    @Setter(AccessLevel.NONE)
    protected String id;
    private int version = -1;
    private final List<BaseEvent> changes = new ArrayList<>();
    final private Logger logger = Logger.getLogger(this.getClass().getName());


    public List<BaseEvent> getUncommittedChanges() {
        return this.changes;
    }

    // this is usually called when the aggregate object is persistent in the eventStore, to prevent duplicate changes from
    // being persisted
    public void makeChangesAsCommited() {
        this.changes.clear();
    }

    protected void applyChange(BaseEvent event, boolean isNewEvent) {
        try {
            // extracts the apply method on the concrete class
            Method method = this.getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException ex) {
            logger.warning(String.format("Unable to apply event %s to %s", event.getClass().getName(), this.getClass().getName()));
        } catch (Exception ex) {
            logger.severe(String.format("Error applying event %s to %s ,%s", event.getClass().getName(), this.getClass().getName(), ex));
        } finally {
            if(isNewEvent){
                // add the new change to the list of uncommitedChanges
                this.changes.add(event);
            }

        }
    }

    public void raiseEvent(BaseEvent event) {
        applyChange(event, true);
    }

    public void replayEvent(Iterable<BaseEvent> events) {
        for (BaseEvent event : events) {
            // do not reapply changes to the event because these changes have already been applied
            // this allows the changes , not to be added to the list of uncommitted changes
            applyChange(event, false);
        }
    }
}
