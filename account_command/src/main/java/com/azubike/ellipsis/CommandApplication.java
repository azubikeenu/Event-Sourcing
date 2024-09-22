package com.azubike.ellipsis;

import com.azubike.ellipsis.api.commands.*;
import com.azubike.ellipsis.infrastructure.AccountCommandDispatcher;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class CommandApplication {
    private final AccountCommandDispatcher dispatcher;
    private final CommandHandler commandHandler;

    public static void main(String[] args) {
        SpringApplication.run(CommandApplication.class, args);
    }


    @PostConstruct
    public void registerHandlers(){
         dispatcher.registerHandler(OpenAccountCommand.class , commandHandler::handle);
         dispatcher.registerHandler(DepositFundsCommand.class , commandHandler::handle);
         dispatcher.registerHandler(WithdrawFundsCommand.class , commandHandler::handle);
         dispatcher.registerHandler(CloseAccountCommand.class , commandHandler::handle);
    }
}
