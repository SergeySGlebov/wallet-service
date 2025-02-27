package ru.glebov.wallet_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.glebov.wallet_service.model.Wallet;
import ru.glebov.wallet_service.repository.WalletRepository;
import ru.glebov.wallet_service.to.IncomingData;
import ru.glebov.wallet_service.util.ValidationUtil;
import ru.glebov.wallet_service.util.exception.IllegalRequestDataException;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {

    private final Logger log = LoggerFactory.getLogger(WalletService.class);

    private final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    private Wallet get(UUID walletId) {
        return ValidationUtil.checkNotFoundWithId(repository.findById(walletId).orElse(null), walletId);
    }

    @Transactional
    public BigDecimal getAmount(UUID walletId) {
        log.info("get amount of wallet {}", walletId);
        return get(walletId).getAmount();
    }

    @Transactional
    public Wallet update(IncomingData incomingData) {
        log.info("apply operation {}", incomingData);
        Wallet wallet = get(incomingData.walletId());
        BigDecimal newAmount = incomingData.operationType().apply(wallet.getAmount(), incomingData.amount());
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalRequestDataException("Недостаточно средств в кошельке");
        }
        wallet.setAmount(newAmount);
        return repository.save(wallet);

    }
}
