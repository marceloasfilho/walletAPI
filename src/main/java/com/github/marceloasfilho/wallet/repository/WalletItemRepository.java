package com.github.marceloasfilho.wallet.repository;

import com.github.marceloasfilho.wallet.entity.WalletItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletItemRepository extends JpaRepository<WalletItem, Long> {
}
