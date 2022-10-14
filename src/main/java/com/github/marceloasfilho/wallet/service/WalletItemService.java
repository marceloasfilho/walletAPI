package com.github.marceloasfilho.wallet.service;

import com.github.marceloasfilho.wallet.entity.WalletItem;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface WalletItemService {
    WalletItem save(WalletItem walletItem);
    Page<WalletItem> findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Long wallet, LocalDate startDate, LocalDate endDate, int page);
}
