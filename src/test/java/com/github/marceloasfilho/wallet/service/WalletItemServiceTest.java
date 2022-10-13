package com.github.marceloasfilho.wallet.service;

import com.github.marceloasfilho.wallet.entity.Wallet;
import com.github.marceloasfilho.wallet.entity.WalletItem;
import com.github.marceloasfilho.wallet.enums.WalletItemTypeEnum;
import com.github.marceloasfilho.wallet.repository.WalletItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class WalletItemServiceTest {
    @InjectMocks
    @Autowired
    private WalletItemServiceImpl walletItemService;

    @Mock
    private WalletItemRepository walletItemRepository;

    private WalletItem walletItem;

    @Before
    public void setup() {
        Wallet wallet = new Wallet();
        wallet.setName("Carteira 1");
        wallet.setValue(BigDecimal.valueOf(500));
        this.walletItem = new WalletItem(wallet, LocalDate.now(), WalletItemTypeEnum.INPUT, "CONTA DE LUZ", BigDecimal.valueOf(65));
        openMocks(this);
    }

    @Test
    public void deveSalvarWalletItem() {
        // Cenário
        when(this.walletItemRepository.save(any(WalletItem.class))).thenReturn(this.walletItem);

        // Ação
        WalletItem savedWalletItem = this.walletItemService.save(this.walletItem);

        // Verificação
        assertNotNull(savedWalletItem);
        assertEquals(savedWalletItem.getDescription(), "CONTA DE LUZ");
        assertEquals(savedWalletItem.getValue().compareTo(BigDecimal.valueOf(65)), 0);
    }
}
