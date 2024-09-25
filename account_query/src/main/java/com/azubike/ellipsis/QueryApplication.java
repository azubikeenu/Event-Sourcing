package com.azubike.ellipsis;

import com.azubike.ellipsis.api.queries.*;
import com.azubike.ellipsis.infrastructure.AccountQueryDispatcher;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class QueryApplication {
    private final AccountQueryDispatcher queryDispatcher;
    private final AccountQueryHandler accountQueryHandler;
    public static void main(String[] args) {
        SpringApplication.run(QueryApplication.class, args);
    }


    @PostConstruct
    public void registerQueries() {
        queryDispatcher.register(FindAccountWithBalanceQuery.class , accountQueryHandler::handle);
        queryDispatcher.register(FindAccountByHolderQuery.class , accountQueryHandler::handle);
        queryDispatcher.register(FindAccountByIdQuery.class , accountQueryHandler::handle);
        queryDispatcher.register(FindAllAccountQuery.class , accountQueryHandler::handle);
    }
}
