package com.tv.techbank.account.cmd.infra;

import com.tv.techbank.cqrs.core.command.BaseCommand;
import com.tv.techbank.cqrs.core.command.CommandHandlerMethod;
import com.tv.techbank.cqrs.core.infra.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//Concret Mediator, implementation of Command Dispatcher mediator interface
@Service
public class AccountCommandDispatcher implements CommandDispatcher {
    private Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handlerMethod) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handlerMethod);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if(handlers == null || handlers.size() == 0){
            throw new RuntimeException("no handler attached");
        }

        if(handlers.size() >1 ){
            throw  new RuntimeException("multiple handlers created, so we can send command to more than one handlers");
        }

        handlers.get(0).handle(command);
    }
}
