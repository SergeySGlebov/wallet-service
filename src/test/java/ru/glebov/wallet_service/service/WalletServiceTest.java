package ru.glebov.wallet_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.glebov.wallet_service.WalletTestData;
import ru.glebov.wallet_service.model.Wallet;
import ru.glebov.wallet_service.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WalletServiceTest {

    @Autowired
    private WalletService service;

    @Test
    void getAmount() {
        assertEquals(WalletTestData.INITIAL_AMOUNT, service.getAmount(WalletTestData.TEST_WALLET_UUID));
    }

    @Test
    void getAmountNotFound() {
        assertThrows(NotFoundException.class, () -> service.getAmount(WalletTestData.NOT_FOUND_UUID));
    }

    @Test
    void updateDeposit() {
        Wallet updated = service.update(WalletTestData.incomingDeposit);
        assertEquals(WalletTestData.testWalletAfterDeposit, updated);
    }

    @Test
    void updateWithdraw() {
        Wallet updated = service.update(WalletTestData.incomingWithdraw);
        assertEquals(WalletTestData.testWalletAfterWithdraw, updated);
    }

    @Test
    void updateNotFound() {
        assertThrows(NotFoundException.class, () -> service.update(WalletTestData.incomingNotFound));
    }
}