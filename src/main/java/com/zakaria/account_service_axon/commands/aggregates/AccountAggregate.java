package com.zakaria.account_service_axon.commands.aggregates;


import com.zakaria.account_service_axon.commonApi.commands.CreateAccountCommand;
import com.zakaria.account_service_axon.commonApi.commands.CreditAccountCommand;
import com.zakaria.account_service_axon.commonApi.commands.DebitAccountCommand;
import com.zakaria.account_service_axon.commonApi.enums.AccountStatus;
import com.zakaria.account_service_axon.commonApi.events.AccountCreatedEvent;
import com.zakaria.account_service_axon.commonApi.events.AccountCreditedEvent;
import com.zakaria.account_service_axon.commonApi.exceptions.NegativeBalanceException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus accountStatus;

    public AccountAggregate() {
        // Required by axon
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        // decision
        if (command.getInitialBalance()<0) throw new NegativeBalanceException("Negative balance");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getCurrency(),
                command.getInitialBalance(),
                AccountStatus.CREATED
        ));
    }
    // evolution function
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getId();
        this.balance = event.getBalance();
        this.currency = event.getCurrency();
        this.accountStatus = event.getStatus();
    }
    @CommandHandler
    public void handle(CreditAccountCommand command) {
        if (command.getAmount()<0) throw new NegativeBalanceException("Negative balance");
        AggregateLifecycle.apply(new CreditAccountCommand(
                command.getId(),
                command.getCurrency(),
                command.getAmount()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        this.balance = this.balance + event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command) {
        if (command.getAmount()<0) throw new NegativeBalanceException("Negative balance");
        if (command.getAmount()> this.balance) throw new NegativeBalanceException("Balance insufficient");
        AggregateLifecycle.apply(new CreditAccountCommand(
                command.getId(),
                command.getCurrency(),
                command.getAmount()
        ));
    }

    @EventSourcingHandler
    public void on(DebitAccountCommand event) {
        this.balance = this.balance - event.getAmount();
    }
}
