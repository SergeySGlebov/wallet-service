package ru.glebov.wallet_service.to;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import ru.glebov.wallet_service.OperationType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record IncomingData(@NotNull UUID walletId,
                           @NotNull OperationType operationType,
                           @NotNull @DecimalMin(value = "0.0", inclusive = false)
                           @Digits(integer = 13, fraction = 2) BigDecimal amount) {
}
