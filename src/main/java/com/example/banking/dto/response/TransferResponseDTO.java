package com.example.banking.dto.response;

import java.time.LocalDateTime;

public record TransferResponseDTO(
    String message,
    String transactionId,
    LocalDateTime timestamp) {
  public static TransferResponseDTO success(String transactionId, LocalDateTime timestamp) {
    return new TransferResponseDTO("Transfer completed successfully", transactionId, timestamp);
  }
}
