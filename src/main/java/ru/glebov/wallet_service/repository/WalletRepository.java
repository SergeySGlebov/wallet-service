package ru.glebov.wallet_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.glebov.wallet_service.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
}
