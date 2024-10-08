package com.zakaria.account_service_axon.commonApi.commands;

import lombok.Getter;

public class DebitAccountCommand extends BaseCommand<String>{

    @Getter private String currency;
    @Getter private double amount;

    public DebitAccountCommand(String id, String currency, double amount) {
        super(id);
        this.currency = currency;
        this.amount = amount;
    }
}
