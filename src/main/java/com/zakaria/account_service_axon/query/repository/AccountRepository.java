package com.zakaria.account_service_axon.query.repository;

import com.zakaria.account_service_axon.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
