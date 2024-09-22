package com.azubike.ellipsis.producers;

import com.azubike.ellipsis.events.BaseEvent;

public interface EventProducer{
    void produce(String topic , BaseEvent event);
}
