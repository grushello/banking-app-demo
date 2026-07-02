package com.example.banking.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.banking.model.Transaction;
import com.example.banking.model.TransactionType;

public record TransactionResponse(
    UUID id,
    String iban,
    TransactionType type,
    BigDecimal amount,
    LocalDateTime createdAt,
    String note) {
  public static TransactionResponse from(Transaction transaction) {
    return new TransactionResponse(
        transaction.getId(),
        transaction.getAccount().getIban(),
        transaction.getType(),
        transaction.getAmount(),
        transaction.getCreatedAt(),
        transaction.getNote());
  }
}
