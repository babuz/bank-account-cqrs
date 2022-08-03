package com.tv.techbank.cqrs.core.events;

import com.tv.techbank.cqrs.core.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEvent extends Message {
    private String version;
}
