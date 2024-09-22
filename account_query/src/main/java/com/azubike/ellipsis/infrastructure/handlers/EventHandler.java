package com.azubike.ellipsis.infrastructure.handlers;

import com.azubike.ellipsis.events.AccountClosedEvent;
import com.azubike.ellipsis.events.AccountOpenedEvent;
import com.azubike.ellipsis.events.FundsDepositedEvent;
import com.azubike.ellipsis.events.FundsWithdrawnEvent;

/**
 * This provides an interface for events that have been successfully published by the
 * AccountsCommand service and consumed by the AccountsQuery service
 * The EventHandler resides on the query side of CQRS and affects the read database
 * While the EventSourcing Handler resides on the command side of the CQRS architecture
 * and affects the write database [ie the eventSource]
 */
public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent  event);
}
