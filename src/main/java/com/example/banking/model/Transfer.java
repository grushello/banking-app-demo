package com.example.banking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class Transfer {
    private final UUID id;
    private final Account account;
    private final TransferType type;
    private final BigDecimal amount;
    private final LocalDateTime createdAt;
    private final String note;
}