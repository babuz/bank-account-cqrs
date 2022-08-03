package com.tv.techbank.cqrs.core.infra;

import com.tv.techbank.cqrs.core.command.BaseCommand;
import com.tv.techbank.cqrs.core.command.CommandHandlerMethod;
//Using Mediator Pattern (This is mediator interface)
public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler (Class<T> type, CommandHandlerMethod<T> handlerMethod);
    void send(BaseCommand command);
}
