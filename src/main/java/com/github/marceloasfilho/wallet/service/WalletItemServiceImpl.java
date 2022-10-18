package com.github.marceloasfilho.wallet.service;

import com.github.marceloasfilho.wallet.entity.WalletItem;
import com.github.marceloasfilho.wallet.enums.WalletItemTypeEnum;
import com.github.marceloasfilho.wallet.repository.WalletItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<WalletItem> findByWalletIdAndType(Long wallet, WalletItemTypeEnum walletItemTypeEnum) {
        return this.walletItemRepository.findByWalletIdAndType(wallet, walletItemTypeEnum);
    }

    @Override
    public BigDecimal sumByWalletId(Long walletId) {
        return this.walletItemRepository.sumByWalletId(walletId);
    }

    @Override
    public Optional<WalletItem> findById(Long walletItemId) {
        return this.walletItemRepository.findById(walletItemId);
    }

    @Override
    public void deleteById(Long wallet) {
        this.walletItemRepository.deleteById(wallet);
    }
}
