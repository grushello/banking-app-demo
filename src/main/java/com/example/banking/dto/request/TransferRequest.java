package com.example.banking.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferRequest(

        @NotBlank
        String fromAccountId,

        @NotBlank
        String toAccountId,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount,

        String note
) {
}