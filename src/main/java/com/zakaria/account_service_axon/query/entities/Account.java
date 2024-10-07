package com.zakaria.account_service_axon.query.entities;

import com.zakaria.account_service_axon.commonApi.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class Account {

    @Id
    private String id;
    private Instant createdAt;
    private Double balance;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @OneToMany(mappedBy = "account")
    private List<AccountTransaction> transactions;


}
