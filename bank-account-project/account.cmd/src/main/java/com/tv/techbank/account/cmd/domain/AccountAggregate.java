package com.tv.techbank.account.cmd.domain;

import com.tv.techbank.account.cmd.api.commands.CloseAccountCommand;
import com.tv.techbank.account.cmd.api.commands.DepositFundsCommand;
import com.tv.techbank.account.cmd.api.commands.OpenAccountCommand;
import com.tv.techbank.account.cmd.api.commands.WithdrawFundsCommand;
import com.tv.techbank.account.common.events.AccountClosedEvent;
import com.tv.techbank.account.common.events.AccountOpenedEvent;
import com.tv.techbank.account.common.events.FundsDepositedEvent;
import com.tv.techbank.account.common.events.FundsWithdrawnEvent;
import com.tv.techbank.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.security.InvalidParameterException;
import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private double balance;
    private boolean active;

    public AccountAggregate(OpenAccountCommand openAccountCommand){
        raiseEvent(AccountOpenedEvent.builder()
                .id(openAccountCommand.getId())
                .accountHolder(openAccountCommand.getAccountHolderName())
                .accountType(openAccountCommand.getAccountType())
                .openingBalance(openAccountCommand.getOpeningBalance())
                .createdDate(new Date())
                .build());
    }

    public void apply(AccountOpenedEvent event){
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(DepositFundsCommand command){
        if(!this.active){
            throw new IllegalStateException("can not deposit amount to closed accounts");
        }
        if(command.getAmount() <= 0 ){
            throw new InvalidParameterException("Deposit amount should be greater than 0");
        }
        raiseEvent(FundsDepositedEvent.builder()
                .amount(command.getAmount())
                .id(command.getId())
                .build());
    }

    public void apply(FundsDepositedEvent event){
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdraFunds(WithdrawFundsCommand command){
        if(!this.active){
            throw new IllegalStateException("can not withdraw amount to closed accounts");
        }
        if(command.getAmount() <= 0 ){
            throw new InvalidParameterException("Withdraw amount should be greater than 0");
        }

        raiseEvent(FundsWithdrawnEvent.builder()
                .id(command.getId())
                .amount(command.getAmount())
                .build());
    }

    public void apply(FundsWithdrawnEvent event){
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount(CloseAccountCommand command){
        if(!this.active){
            throw new IllegalStateException("can not close account which is already closed");
        }
        raiseEvent(AccountClosedEvent.builder()
                .id(command.getId())
                .build());
    }

    public void apply(AccountClosedEvent event){
        this.id = event.getId();
        this.active = false;
    }

}
