package ru.glebov.wallet_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.glebov.wallet_service.model.Wallet;
import ru.glebov.wallet_service.repository.WalletRepository;
import ru.glebov.wallet_service.to.IncomingData;
import ru.glebov.wallet_service.util.ValidationUtil;

import java.math.BigDecimal;

@Service
public class WalletService {
    private final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    public Wallet get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Transactional
    public void update(IncomingData incomingData) {
        Wallet wallet = get(incomingData.id());
        BigDecimal newAmount = incomingData.operationType().apply(wallet.getAmount(), incomingData.amount());
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException();
        }
        wallet.setAmount(newAmount);
        repository.save(wallet);
    }
}
