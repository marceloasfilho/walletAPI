package com.github.marceloasfilho.wallet.service;

import com.github.marceloasfilho.wallet.repository.WalletItemRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class WalletItemServiceTest {

    @Autowired
    WalletItemService walletItemService;

    @Autowired
    WalletItemRepository walletItemRepository;
}
