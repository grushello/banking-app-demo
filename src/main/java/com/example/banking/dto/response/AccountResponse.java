package com.example.banking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class AccountResponse {
    private UUID id;
    private String iban;
    private String ownerName;
    private BigDecimal balance;
}
