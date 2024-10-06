package com.zakaria.account_service_axon.commands.controllers;


import com.zakaria.account_service_axon.commonApi.commands.CreateAccountCommand;
import com.zakaria.account_service_axon.commonApi.commands.CreditAccountCommand;
import com.zakaria.account_service_axon.commonApi.commands.DebitAccountCommand;
import com.zakaria.account_service_axon.commonApi.dtos.CreateAccountRequestDTO;
import com.zakaria.account_service_axon.commonApi.dtos.CreditAccountRequestDTO;
import com.zakaria.account_service_axon.commonApi.dtos.DebitAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/account")
public class AccountCommandController {

    private CommandGateway commandGateway;

    private EventStore eventStore;
    public AccountCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public CompletableFuture<String> createNewAccount(@RequestBody CreateAccountRequestDTO request) {
        return this.commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getCurrency(),
                request.getInitialBalance()
        ));
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request) {
        return this.commandGateway.send(new DebitAccountCommand(
                request.getAccountId(),
                request.getCurrency(),
                request.getAmount()
        ));
    }

    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request) {
        return this.commandGateway.send(new CreditAccountCommand(
                request.getAccountId(),
                request.getCurrency(),
                request.getAmount()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/eventStore/{id}")
    public Stream eventStore(@PathVariable String id) {
        return eventStore.readEvents(id).asStream();
    }
}
