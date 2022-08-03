package com.tv.techbank.cqrs.core.command;

import com.tv.techbank.cqrs.core.message.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public abstract class BaseCommand extends Message {
    public BaseCommand(String id){
        super(id);
    }
}
