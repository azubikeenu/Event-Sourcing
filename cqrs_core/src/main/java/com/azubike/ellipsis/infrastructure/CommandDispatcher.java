package com.azubike.ellipsis.infrastructure;

import com.azubike.ellipsis.commands.BaseCommand;
import com.azubike.ellipsis.commands.CommandHandlerMethod;

public interface CommandDispatcher {
   <T extends BaseCommand>void registerHandler(Class<T> type , CommandHandlerMethod<T> handlerMethod);
    void send(BaseCommand command);
}
