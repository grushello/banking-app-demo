package com.example.banking.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.banking.model.Transaction;

public record TransferResponse(
    UUID outgoingTransactionId,
    UUID incomingTransactionId,
    UUID fromAccountId,
    String fromIban,
    UUID toAccountId,
    String toIban,
    BigDecimal amount,
    LocalDateTime createdAt,
    String note) {
  public static TransferResponse from(Transaction outgoing, Transaction incoming) {
    return new TransferResponse(
        outgoing.getId(),
        incoming.getId(),
        outgoing.getAccount().getId(),
        outgoing.getAccount().getIban(),
        incoming.getAccount().getId(),
        incoming.getAccount().getIban(),
        outgoing.getAmount(),
        outgoing.getCreatedAt(),
        outgoing.getNote());
  }
}
