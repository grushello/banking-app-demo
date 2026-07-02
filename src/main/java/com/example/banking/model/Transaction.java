package com.example.banking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Transaction {
    private final UUID id;
    private final Account account;
    private final TransactionType type;
    private final BigDecimal amount;
    private final LocalDateTime createdAt;
    private final String note;
}