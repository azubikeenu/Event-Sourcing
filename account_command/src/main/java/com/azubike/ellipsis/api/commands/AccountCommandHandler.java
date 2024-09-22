package com.azubike.ellipsis.api.commands;
import com.azubike.ellipsis.domain.AccountAggregate;
import com.azubike.ellipsis.handlers.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountCommandHandler implements CommandHandler {
    private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;
    
    @Override
    public void handle(OpenAccountCommand command) {
        // this invokes the raiseEvent method on the account aggregate
        // and updates the state of the aggregate object
        var aggregate = new AccountAggregate(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(DepositFundsCommand command) {
        AccountAggregate  accountAggregate = eventSourcingHandler.getById(command.getId());
        accountAggregate.depositFunds(command.getAmount());
        // Save the latest state of the aggregate
        eventSourcingHandler.save(accountAggregate);
    }

    @Override
    public void handle(WithdrawFundsCommand command) {
        AccountAggregate accountAggregate = eventSourcingHandler.getById(command.getId());
        if(accountAggregate.getBalance() < command.getAmount()) {
            throw new IllegalArgumentException("Not enough balance");
        }
        accountAggregate.withdrawFunds(command.getAmount());
        // Save the latest state of the aggregate
        eventSourcingHandler.save(accountAggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
       AccountAggregate accountAggregate = eventSourcingHandler.getById(command.getId());
       accountAggregate.closeAccount();
       // Save the latest state of the aggregate
       eventSourcingHandler.save(accountAggregate);
    }
}
