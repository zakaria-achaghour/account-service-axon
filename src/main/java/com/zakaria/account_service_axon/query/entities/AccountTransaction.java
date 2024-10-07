package com.zakaria.account_service_axon.query.entities;

import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class AccountTransaction {

    private Long id;
    private Date timestamp;
    private double amount;
    private TransactionType transactionType;
}
