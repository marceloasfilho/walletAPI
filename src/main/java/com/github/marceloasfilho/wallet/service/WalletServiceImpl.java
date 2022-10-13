package com.github.marceloasfilho.wallet.service;

import com.github.marceloasfilho.wallet.entity.Wallet;
import com.github.marceloasfilho.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public void save(Wallet wallet) {
        this.walletRepository.save(wallet);
    }
}
