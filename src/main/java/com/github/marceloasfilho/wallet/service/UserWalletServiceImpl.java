package com.github.marceloasfilho.wallet.service;

import com.github.marceloasfilho.wallet.entity.UserWallet;
import com.github.marceloasfilho.wallet.repository.UserWalletRepository;
import org.springframework.stereotype.Service;

@Service
public class UserWalletServiceImpl implements UserWalletService {

    private final UserWalletRepository userWalletRepository;

    public UserWalletServiceImpl(UserWalletRepository userWalletRepository) {
        this.userWalletRepository = userWalletRepository;
    }

    @Override
    public UserWallet save(UserWallet userWallet) {
        return this.userWalletRepository.save(userWallet);
    }
}
