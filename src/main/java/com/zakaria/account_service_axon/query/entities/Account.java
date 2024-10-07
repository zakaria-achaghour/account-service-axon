package com.zakaria.account_service_axon.query.entities;

import com.zakaria.account_service_axon.commonApi.enums.AccountStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class Account {

    @Id
    private String id;
    private Date createdAt;
    private Double balance;
    private AccountStatus status;
    @OneToMany(mappedBy = "account")
    private List<AccountTransaction> transactions;


}
