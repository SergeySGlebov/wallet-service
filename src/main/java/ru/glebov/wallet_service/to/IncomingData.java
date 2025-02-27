package ru.glebov.wallet_service.to;

import jakarta.validation.constraints.Min;
import ru.glebov.wallet_service.OperationType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record IncomingData(Integer id, @NotNull OperationType operationType, @NotNull @Min(0) BigDecimal amount) {
}
