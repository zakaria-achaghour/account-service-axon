package com.zakaria.account_service_axon.query.services;


import com.zakaria.account_service_axon.commonApi.events.AccountCreatedEvent;
import com.zakaria.account_service_axon.query.entities.Account;
import com.zakaria.account_service_axon.query.queries.GetAllAccounts;
import com.zakaria.account_service_axon.query.repository.AccountRepository;
import com.zakaria.account_service_axon.query.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

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

    @QueryHandler
    public List<Account> on(GetAllAccounts query){
        return accountRepository.findAll();
    }
}
