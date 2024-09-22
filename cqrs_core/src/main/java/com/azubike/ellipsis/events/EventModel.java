package com.azubike.ellipsis.events;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(collection = "eventStore")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EventModel {
    @Id
    private String id;

    private Date timestamp;

    private String aggregateIdentifier;

    private String eventType;

    private String aggregateType;

    private BaseEvent eventData;

    private int version;

}
