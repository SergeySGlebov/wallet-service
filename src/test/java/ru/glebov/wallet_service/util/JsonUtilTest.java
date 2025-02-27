package ru.glebov.wallet_service.util;

import org.junit.jupiter.api.Test;
import ru.glebov.wallet_service.WalletTestData;
import ru.glebov.wallet_service.model.Wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {

    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(WalletTestData.testWallet);
        System.out.println(json);
        Wallet wallet = JsonUtil.readValue(json, Wallet.class);
        assertEquals(WalletTestData.testWallet, wallet);
    }

}
