package ru.glebov.wallet_service.to;

import jakarta.validation.constraints.Min;
import ru.glebov.wallet_service.OperationType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record IncomingData(@NotNull UUID walletId, @NotNull OperationType operationType, @NotNull @Min(0) BigDecimal amount) {
}
