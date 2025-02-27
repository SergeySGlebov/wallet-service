package ru.glebov.wallet_service;

import ru.glebov.wallet_service.model.Wallet;
import ru.glebov.wallet_service.to.IncomingData;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletTestData {
    public static final BigDecimal INITIAL_AMOUNT = BigDecimal.valueOf(500);
    public static final BigDecimal DELTA = BigDecimal.valueOf(178.95);

    public static final UUID TEST_WALLET_UUID = UUID.fromString("6148dd58-694e-4781-ac6c-7670855d11c5");
    public static final UUID NOT_FOUND_UUID = UUID.fromString("6148dd58-694e-4781-ac6c-7670855d11c4");
    public static final Wallet testWallet = new Wallet(TEST_WALLET_UUID, INITIAL_AMOUNT);
    public static final Wallet testWalletAfterDeposit = new Wallet(TEST_WALLET_UUID, INITIAL_AMOUNT.add(DELTA));
    public static final Wallet testWalletAfterWithdraw = new Wallet(TEST_WALLET_UUID, INITIAL_AMOUNT.subtract(DELTA));

    public static final IncomingData incomingDeposit = new IncomingData(TEST_WALLET_UUID, OperationType.DEPOSIT, DELTA);
    public static final IncomingData incomingWithdraw = new IncomingData(TEST_WALLET_UUID, OperationType.WITHDRAW, DELTA);
    public static final IncomingData incomingNotFound = new IncomingData(NOT_FOUND_UUID, OperationType.DEPOSIT, DELTA);
}
