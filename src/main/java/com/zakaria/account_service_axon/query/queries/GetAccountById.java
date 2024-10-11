package com.zakaria.account_service_axon.query.queries;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetAccountById {
    private String id;

    public GetAccountById(String id) {
        this.id = id;
    }
}
