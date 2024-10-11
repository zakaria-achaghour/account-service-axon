package com.zakaria.account_service_axon.query.services;


import com.zakaria.account_service_axon.commonApi.enums.TransactionType;
import com.zakaria.account_service_axon.commonApi.events.AccountCreatedEvent;
import com.zakaria.account_service_axon.commonApi.events.AccountCreditedEvent;
import com.zakaria.account_service_axon.commonApi.events.AccountDebitedEvent;
import com.zakaria.account_service_axon.query.entities.Account;
import com.zakaria.account_service_axon.query.entities.AccountTransaction;
import com.zakaria.account_service_axon.query.queries.GetAccountById;
import com.zakaria.account_service_axon.query.queries.GetAllAccounts;
import com.zakaria.account_service_axon.query.repository.AccountRepository;
import com.zakaria.account_service_axon.query.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class AccountEventHandlerService {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public AccountEventHandlerService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage<AccountCreatedEvent> eventMessage) {

        log.info("***************************************");
        log.info("AccountCreatedEvent  received");
        Account account = new Account();
        account.setId(event.getId());
        account.setBalance(event.getBalance());
        account.setCurrency(event.getCurrency());
        account.setStatus(event.getStatus());
        account.setCreatedAt(eventMessage.getTimestamp());
        accountRepository.save(account);
    }



    @EventHandler
    public void on(AccountDebitedEvent event, EventMessage<AccountDebitedEvent> eventMessage) {

        log.info("***************************************");
        log.info("AccountDebitedEvent  received");
        Account account = accountRepository.findById(event.getId()).get();
        AccountTransaction transaction = new AccountTransaction();
        transaction.setAmount(event.getAmount());
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setTimestamp(Date.from(eventMessage.getTimestamp()));
        transaction.setAccount(account);
        transactionRepository.save(transaction);

        account.setBalance(account.getBalance() - event.getAmount());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event, EventMessage<AccountCreditedEvent> eventMessage) {

        log.info("***************************************");
        log.info("AccountCreditedEvent  received");
        Account account = accountRepository.findById(event.getId()).get();
        AccountTransaction transaction = new AccountTransaction();
        transaction.setAmount(event.getAmount());
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setTimestamp(Date.from(eventMessage.getTimestamp()));
        transaction.setAccount(account);
        transactionRepository.save(transaction);

        account.setBalance(account.getBalance() + event.getAmount());
        accountRepository.save(account);
    }
    @QueryHandler
    public List<Account> on(GetAllAccounts query){
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountById query){
        return accountRepository.findById(query.getId()).get();
    }
}
