package com.example.banking.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class CreateAccountRequest {

    @NotBlank(message = "Owner name is required")
    private String ownerName;

    @DecimalMin(value = "0.0", inclusive = true,
            message = "Initial balance cannot be negative")
    private BigDecimal balance;
}