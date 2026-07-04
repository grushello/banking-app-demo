package com.example.banking.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class CreateAccountRequest {

    @NotBlank(message = "Owner name is required")
    private String ownerName;

    @NotNull(message = "Initial balance is required")
    @DecimalMin(value = "0.0", inclusive = true,
            message = "Initial balance cannot be negative")
    private BigDecimal balance;
}