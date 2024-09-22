package com.azubike.ellipsis.infrastructure.consumer;

import com.azubike.ellipsis.events.AccountClosedEvent;
import com.azubike.ellipsis.events.AccountOpenedEvent;
import com.azubike.ellipsis.events.FundsDepositedEvent;
import com.azubike.ellipsis.events.FundsWithdrawnEvent;
import com.azubike.ellipsis.infrastructure.handlers.AccountEventsHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountEventConsumer implements EventConsumer {

    private final AccountEventsHandler accountEventsHandler;

    @KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload  AccountOpenedEvent accountOpenedEvent, Acknowledgment acknowledgment) {
        log.info("Received AccountOpenedEvent: {}", accountOpenedEvent);
        accountEventsHandler.on(accountOpenedEvent);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "FundsDepositedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload  FundsDepositedEvent fundsDepositedEvent, Acknowledgment acknowledgment) {
        accountEventsHandler.on(fundsDepositedEvent);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "FundsWithdrawnEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload  FundsWithdrawnEvent fundsWithdrawnEvent, Acknowledgment acknowledgment) {
        accountEventsHandler.on(fundsWithdrawnEvent);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload AccountClosedEvent event, Acknowledgment ack) {
        accountEventsHandler.on(event);
        ack.acknowledge();
    }
}
