package com.github.marceloasfilho.wallet.repository;

import com.github.marceloasfilho.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
