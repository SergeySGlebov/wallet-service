package ru.glebov.wallet_service;

import java.math.BigDecimal;

public enum OperationType {
    DEPOSIT(1),
    WITHDRAW(-1);
    private final int multiplier;

    OperationType(int multiplier) {
        this.multiplier = multiplier;
    }

    public BigDecimal apply(BigDecimal first, BigDecimal second) {
        return first.add(second.multiply(BigDecimal.valueOf(multiplier)));
    }
}
