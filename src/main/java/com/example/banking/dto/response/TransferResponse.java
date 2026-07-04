package com.example.banking.dto.response;

import com.example.banking.model.Transfer;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferResponse(
        String id,
        String type,
        BigDecimal amount,

        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd'T'HH:mm:ss"
        )
        LocalDateTime createdAt,

        String note
) {

    public static TransferResponse fromEntity(Transfer transfer) {
        return new TransferResponse(
                transfer.getId().toString(),
                transfer.getType().name(),
                transfer.getAmount(),
                transfer.getCreatedAt(),
                transfer.getNote()
        );
    }
}