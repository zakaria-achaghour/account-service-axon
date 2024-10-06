package com.zakaria.account_service_axon.commonApi.events;

import com.zakaria.account_service_axon.commonApi.enums.AccountStatus;
import lombok.Getter;

public class AccountDebitedEvent extends BaseEvent<String> {
    @Getter private String currency;
    @Getter private double amount;
    public AccountDebitedEvent(String id, String currency, double amount) {
        super(id);
        this.currency = currency;
        this.amount = amount;
    }
}
