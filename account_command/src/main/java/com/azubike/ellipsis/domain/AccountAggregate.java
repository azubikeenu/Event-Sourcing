package com.azubike.ellipsis.domain;

import com.azubike.ellipsis.api.commands.OpenAccountCommand;
import com.azubike.ellipsis.events.AccountClosedEvent;
import com.azubike.ellipsis.events.AccountOpenedEvent;
import com.azubike.ellipsis.events.FundsDepositedEvent;
import com.azubike.ellipsis.events.FundsWithdrawnEvent;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    private boolean active;
    private double balance;


    public AccountAggregate(OpenAccountCommand command) {
        // create an event
         raiseEvent(AccountOpenedEvent.builder().accountType(command.getAccountType())
                .accountHolder(command.getAccountHolder())
                .openingBalance(command.getOpeningBalance()).createdDate(new Date())
                .id(command.getId())
                .build());

    }


    // create an apply method for the AccountOpenedEvent
    public void apply(AccountOpenedEvent event) {
        this.active = true;
        this.balance = event.getOpeningBalance();
        this.id = event.getId();

    }


    public void depositFunds(double amount) {
        if(!this.active){
            throw new IllegalStateException("Cannot deposit funds to an inactive account");
        }
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        // create an event
        FundsDepositedEvent ev = FundsDepositedEvent.builder().amount(amount).id(this.getId()).build();
        raiseEvent(ev);

    }


    // create an apply method for the FundsDepositedEvent
    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance+= event.getAmount();
    }



    public void withdrawFunds(double amount) {
        if(!this.active){
            throw new IllegalStateException("Cannot withdrawn funds to an inactive account");
        }
        if(this.balance <= 0){
            throw new IllegalArgumentException("Funds cannot be withdrawn from an account with zero balance");
        }
        // create an event
        raiseEvent(FundsWithdrawnEvent.builder().amount(amount).id(this.getId()).build());

    }


    // create an apply method for the FundsDepositedEvent
    public void apply(FundsWithdrawnEvent event) {
        this.id = event.getId();
        this.balance-= event.getAmount();
    }


    public void closeAccount() {
        if(!this.active){
            throw new IllegalStateException("Account is not active");
        }
        raiseEvent(AccountClosedEvent.builder().id(this.getId()).build());
    }


    // create an apply method for the AccountClosedEvent
    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }


    public double getBalance() {
        return this.balance;
    }

}
