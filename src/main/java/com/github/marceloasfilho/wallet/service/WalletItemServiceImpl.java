package com.github.marceloasfilho.wallet.service;

import com.github.marceloasfilho.wallet.entity.WalletItem;
import com.github.marceloasfilho.wallet.repository.WalletItemRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletItemServiceImpl implements WalletItemService {
    final WalletItemRepository walletItemRepository;
    public WalletItemServiceImpl(WalletItemRepository walletItemRepository) {
        this.walletItemRepository = walletItemRepository;
    }
    @Override
    public WalletItem save(WalletItem walletItem) {
        return this.walletItemRepository.save(walletItem);
    }
}
