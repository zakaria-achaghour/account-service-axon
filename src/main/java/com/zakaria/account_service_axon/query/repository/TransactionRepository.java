package com.zakaria.account_service_axon.query.repository;

import com.zakaria.account_service_axon.query.entities.Account;
import com.zakaria.account_service_axon.query.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<AccountTransaction, Long> {
}
