package com.example.banking.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequestDTO(
    UUID fromAccountId,
    UUID toAccountId,
    BigDecimal amount,
    String note) {
}
