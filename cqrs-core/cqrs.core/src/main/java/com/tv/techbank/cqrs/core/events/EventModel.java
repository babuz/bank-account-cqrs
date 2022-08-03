package com.tv.techbank.cqrs.core.events;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(collection = "eventStore")
public class EventModel {
    @Id
    private String id;
    private int version;
    private String aggregateIdentifier;
    private String eventType;
    private Date timeStamp;
    private BaseEvent eventData;
}
