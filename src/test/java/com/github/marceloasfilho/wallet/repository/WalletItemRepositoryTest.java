package com.github.marceloasfilho.wallet.repository;

import com.github.marceloasfilho.wallet.entity.Wallet;
import com.github.marceloasfilho.wallet.entity.WalletItem;
import com.github.marceloasfilho.wallet.enums.WalletItemTypeEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class WalletItemRepositoryTest {

    private static final LocalDate DATA = LocalDate.now();
    private static final WalletItemTypeEnum TYPE = WalletItemTypeEnum.INPUT;
    private static final String DESCRIPTION = "CONTA DE LUZ";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);

    @Autowired
    WalletItemRepository walletItemRepository;
    @Autowired
    WalletRepository walletRepository;

    @Test
    public void deveSalvarWalletItem() {
        Wallet wallet = new Wallet();
        wallet.setName("Carteira 1");
        wallet.setValue(BigDecimal.valueOf(500));
        this.walletRepository.save(wallet);

        WalletItem walletItem = new WalletItem(wallet, DATA, TYPE, DESCRIPTION, VALUE);
        WalletItem response = this.walletItemRepository.save(walletItem);

        Assert.assertNotNull(response);
        assertEquals(response.getType(), TYPE);
        assertEquals(response.getDescription(), DESCRIPTION);
        assertEquals(response.getDate(), DATA);
        assertEquals(response.getValue(), VALUE);
    }
}
