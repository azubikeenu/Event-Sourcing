package com.azubike.ellipsis.infrastructure;

import com.azubike.ellipsis.commands.BaseCommand;
import com.azubike.ellipsis.commands.CommandHandlerMethod;
import com.azubike.ellipsis.exceptions.HandlerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
    // this is an in-memory persistence of the command handlers
    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handlerMethod) {
        // it creates an empty list, if type [The CommandHandler class name ] does not exist
        var handlers = routes.computeIfAbsent(type, k -> new LinkedList<>());
        // add the handler Method to the new list created ,which is identified by the class type
        handlers.add(handlerMethod);
    }

    @Override
    public void send(BaseCommand command) {

        var handlers = routes.get(command.getClass());
        if(handlers == null || handlers.isEmpty()) {
           throw new HandlerNotFoundException(String.format("Handler %s not found", command.getClass()));
        }

        if(handlers.size() > 1) {
            throw new HandlerNotFoundException(String.format("Multiple handler %s found", command.getClass()));
        }

        // This gets the command and invokes the handle method of the command handler associated with the command
        // Each command handler has a handle method
        // this calls the concrete implementation's handle method
        // Example dispatcher.send(OpenAccountCommand)
        // this would invoke the overloaded handleMethod of the Account's commandHandler class that has the
        // OpenAccountCommand as a parameter [Cool Stuff :)]
         handlers.get(0).handle(command);


    }
}
