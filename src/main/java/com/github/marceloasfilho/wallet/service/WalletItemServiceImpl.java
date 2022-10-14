package com.github.marceloasfilho.wallet.service;

import com.github.marceloasfilho.wallet.entity.WalletItem;
import com.github.marceloasfilho.wallet.repository.WalletItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WalletItemServiceImpl implements WalletItemService {
    private WalletItemRepository walletItemRepository;

    public WalletItemServiceImpl(WalletItemRepository walletItemRepository) {
        this.walletItemRepository = walletItemRepository;
    }

    @Override
    public WalletItem save(WalletItem walletItem) {
        return this.walletItemRepository.save(walletItem);
    }

    @Override
    public Page<WalletItem> findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Long wallet, LocalDate startDate, LocalDate endDate, int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        return this.walletItemRepository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(wallet, startDate, endDate, pageRequest);
    }
}
