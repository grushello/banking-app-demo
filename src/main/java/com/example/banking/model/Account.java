package com.example.banking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Account {
    private final UUID id;
    private final String iban;

    @Setter private String ownerName;
    @Setter private BigDecimal balance;
}