package com.github.marceloasfilho.wallet.service;

import com.github.marceloasfilho.wallet.entity.WalletItem;
import com.github.marceloasfilho.wallet.enums.WalletItemTypeEnum;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WalletItemService {
    WalletItem save(WalletItem walletItem);

    Page<WalletItem> findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Long wallet, LocalDate startDate, LocalDate endDate, int page);

    List<WalletItem> findByWalletIdAndType(Long wallet, WalletItemTypeEnum walletItemTypeEnum);

    BigDecimal sumByWalletId(Long walletId);

    Optional<WalletItem> findById(Long walletItemId);

    void deleteById(Long wallet);
}
