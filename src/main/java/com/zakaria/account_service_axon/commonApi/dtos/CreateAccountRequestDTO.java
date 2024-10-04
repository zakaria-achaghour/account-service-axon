package com.zakaria.account_service_axon.commonApi.dtos;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequestDTO {
    private String currency;
    private double initialBalance;
}
