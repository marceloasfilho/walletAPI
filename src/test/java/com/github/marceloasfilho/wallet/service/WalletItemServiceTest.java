package com.github.marceloasfilho.wallet.service;

import com.github.marceloasfilho.wallet.entity.Wallet;
import com.github.marceloasfilho.wallet.entity.WalletItem;
import com.github.marceloasfilho.wallet.enums.WalletItemTypeEnum;
import com.github.marceloasfilho.wallet.repository.WalletItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dev")
public class WalletItemServiceTest {
    @InjectMocks
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
        Wallet wallet2 = new Wallet();
        wallet2.setName("Carteira 2");
        wallet2.setValue(BigDecimal.valueOf(200));
        WalletItem walletItem2 = new WalletItem(wallet2, LocalDate.now(), WalletItemTypeEnum.OUTPUT, "CONTA DE ÁGUA", BigDecimal.valueOf(80));

        // Mock
        when(this.walletItemRepository.save(any(WalletItem.class))).thenReturn(this.walletItem);

        // Ação
        WalletItem savedWalletItem = this.walletItemService.save(walletItem2);

        // Verificação
        assertNotNull(savedWalletItem);
        assertEquals("CONTA DE LUZ", savedWalletItem.getDescription());
        assertEquals(0, savedWalletItem.getValue().compareTo(BigDecimal.valueOf(65)));
    }

    @Test
    public void deveEncontrarWalletItemEntreDatas() {
        // Cenário
        Page<WalletItem> page = new PageImpl<>(List.of(this.walletItem));

        // Mock
        doReturn(page).when(this.walletItemRepository).findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(anyLong(), any(LocalDate.class), any(LocalDate.class), any(PageRequest.class));

        // Ação
        Page<WalletItem> response = this.walletItemService.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(1L, LocalDate.now(), LocalDate.now(), 1);

        // Verificação
        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals("CONTA DE LUZ", response.getContent().get(0).getDescription());
    }
    @Test
    public void deveEncontrarWalletItemPorTipoDeWallet(){
        // Cenário
        List<WalletItem> walletItems = List.of(this.walletItem);

        // Mock
        when(this.walletItemRepository.findByWalletIdAndType(anyLong(), any(WalletItemTypeEnum.class))).thenReturn(walletItems);

        // Ação
        List<WalletItem> response = this.walletItemService.findByWalletIdAndType(2L, WalletItemTypeEnum.INPUT);

        // Verificação
        assertFalse(response.isEmpty());
        assertEquals("Carteira 1", response.get(0).getWallet().getName());
    }

    @Test
    public void deveSomarPorWalletId(){
        // Cenário
        BigDecimal expected = BigDecimal.valueOf(45);

        // Mock
        when(this.walletItemRepository.sumByWalletId(anyLong())).thenReturn(expected);

        // Ação
        BigDecimal response = this.walletItemService.sumByWalletId(1L);

        // Verificação
        assertEquals(expected, response);
    }
}
