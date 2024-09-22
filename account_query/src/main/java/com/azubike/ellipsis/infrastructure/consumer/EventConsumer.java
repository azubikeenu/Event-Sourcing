package com.azubike.ellipsis.infrastructure.consumer;

import com.azubike.ellipsis.events.AccountClosedEvent;
import com.azubike.ellipsis.events.AccountOpenedEvent;
import com.azubike.ellipsis.events.FundsDepositedEvent;
import com.azubike.ellipsis.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent accountOpenedEvent , Acknowledgment acknowledgment);
    void consume(@Payload FundsDepositedEvent fundsDepositedEvent , Acknowledgment acknowledgment);
    void consume(@Payload FundsWithdrawnEvent fundsWithdrawnEvent , Acknowledgment acknowledgment);
    void consume(@Payload AccountClosedEvent accountClosedEvent , Acknowledgment acknowledgment);
}
