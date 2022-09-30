package com.github.marceloasfilho.wallet.repository;

import com.github.marceloasfilho.wallet.entity.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {
}
