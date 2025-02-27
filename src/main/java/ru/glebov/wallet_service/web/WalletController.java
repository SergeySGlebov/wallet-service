package ru.glebov.wallet_service.web;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.glebov.wallet_service.model.Wallet;
import ru.glebov.wallet_service.service.WalletService;
import ru.glebov.wallet_service.to.IncomingData;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping(WalletController.REST_URL)
public class WalletController {
    public static final String REST_URL = "/api/v1";

    private final WalletService service;

    public WalletController(WalletService walletService) {
        this.service = walletService;
    }

    @PostMapping("/wallet")
    public Wallet changeBalance(@RequestBody @Valid IncomingData incomingData) {
        return service.update(incomingData);
    }

    @GetMapping("wallets/{id}")
    public BigDecimal getAmount(@PathVariable UUID id) {
        return service.getAmount(id);
    }

}
