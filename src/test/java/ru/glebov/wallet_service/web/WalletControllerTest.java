package ru.glebov.wallet_service.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.glebov.wallet_service.WalletTestData;
import ru.glebov.wallet_service.model.Wallet;
import ru.glebov.wallet_service.util.JsonUtil;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WalletControllerTest {

    private static final String REST_URL = WalletController.REST_URL + "/";

    @Autowired
    MockMvc mvc;

    @Test
    void changeBalanceDeposit() throws Exception {
        ResultActions action = mvc.perform(MockMvcRequestBuilders.post(REST_URL + "/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(WalletTestData.incomingDeposit)))
                .andExpect(status().isOk());
        Wallet changed = JsonUtil.readValue(action.andReturn().getResponse().getContentAsString(), Wallet.class);
        assertEquals(WalletTestData.testWalletAfterDeposit, changed);
    }

    @Test
    void changeBalanceWithdraw() throws Exception {
        ResultActions action = mvc.perform(MockMvcRequestBuilders.post(REST_URL + "/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(WalletTestData.incomingWithdraw)))
                .andExpect(status().isOk());
        Wallet changed = JsonUtil.readValue(action.andReturn().getResponse().getContentAsString(), Wallet.class);
        assertEquals(WalletTestData.testWalletAfterWithdraw, changed);
    }

    @Test
    void changeBalanceNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(REST_URL + "/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(WalletTestData.incomingNotFound)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void changeBalanceWrongJson() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(REST_URL + "/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getAmount() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(REST_URL + "wallets/" + WalletTestData.TEST_WALLET_UUID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertEquals(WalletTestData.INITIAL_AMOUNT, new BigDecimal(result.getResponse().getContentAsString())));
    }

    @Test
    void getAmountNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(REST_URL + "wallets/" + WalletTestData.NOT_FOUND_UUID))
                .andExpect(status().isUnprocessableEntity());
    }
}