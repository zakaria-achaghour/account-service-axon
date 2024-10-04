package com.zakaria.account_service_axon.commands.controllers;


import com.zakaria.account_service_axon.commonApi.commands.CreateAccountCommand;
import com.zakaria.account_service_axon.commonApi.dtos.CreateAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/account")
public class AccountCommandController {

    private CommandGateway commandGateway;

    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public CompletableFuture<String> createNewAccount(@RequestBody CreateAccountRequestDTO request) {
        return this.commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getCurrency(),
                request.getInitialBalance()
        ));
    }
}
