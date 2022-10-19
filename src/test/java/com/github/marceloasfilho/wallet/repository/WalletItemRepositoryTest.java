package com.github.marceloasfilho.wallet.repository;

import com.github.marceloasfilho.wallet.entity.Wallet;
import com.github.marceloasfilho.wallet.entity.WalletItem;
import com.github.marceloasfilho.wallet.enums.WalletItemTypeEnum;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class WalletItemRepositoryTest {

    private static final LocalDate DATA = LocalDate.now();
    private static final WalletItemTypeEnum TYPE = WalletItemTypeEnum.INPUT;
    private static final String DESCRIPTION = "CONTA DE LUZ";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);

    @After
    public void close() {
        this.walletItemRepository.deleteAll();
    }

    @Autowired
    WalletItemRepository walletItemRepository;

    @Test
    public void deveSalvarWalletItem() {
        Wallet wallet = new Wallet();
        wallet.setName("Carteira 1");
        wallet.setValue(BigDecimal.valueOf(500));

        WalletItem walletItem = new WalletItem(wallet, DATA, TYPE, DESCRIPTION, VALUE);
        WalletItem response = this.walletItemRepository.save(walletItem);

        Assert.assertNotNull(response);
        assertEquals(response.getType(), TYPE);
        assertEquals(response.getDescription(), DESCRIPTION);
        assertEquals(response.getDate(), DATA);
        assertEquals(response.getValue(), VALUE);
    }

    @Test
    public void naoDeveSalvarWalletItemInvalido() {
        // Cenário
        WalletItem walletItem = new WalletItem();
        walletItem.setId(null);
        walletItem.setWallet(null);
        walletItem.setDate(DATA);
        walletItem.setType(null);
        walletItem.setDescription(DESCRIPTION);
        walletItem.setValue(null);

        // Verificação
        assertThrows(ConstraintViolationException.class, () -> {
            // Ação
            this.walletItemRepository.save(walletItem);
        });
    }

    @Test
    public void deveAtualizarWalletItemPelaDescricao() {
        // Cenário
        Wallet wallet = new Wallet();
        wallet.setName("Carteira 1");
        wallet.setValue(BigDecimal.valueOf(500));

        WalletItem walletItem = new WalletItem(wallet, DATA, TYPE, DESCRIPTION, VALUE);
        this.walletItemRepository.save(walletItem);

        String description = "Descrição Alterada";

        walletItem.setDescription(description);

        // Ação
        this.walletItemRepository.save(walletItem);

        // Verificação
        Optional<WalletItem> newWalletItem = this.walletItemRepository.findById(walletItem.getId());
        assertTrue(newWalletItem.isPresent());
        assertEquals(description, newWalletItem.get().getDescription());
    }

    @Test
    public void deveDeletarWalletItemPeloId() {
        // Cenário
        Wallet wallet = new Wallet();
        wallet.setName("Carteira 1");
        wallet.setValue(BigDecimal.valueOf(500));

        WalletItem walletItem = new WalletItem(wallet, DATA, TYPE, DESCRIPTION, VALUE);
        this.walletItemRepository.save(walletItem);

        // Ação
        this.walletItemRepository.deleteById(walletItem.getId());

        // Verificação
        Optional<WalletItem> deletedWalletById = this.walletItemRepository.findById(walletItem.getId());
        assertFalse(deletedWalletById.isPresent());
    }

    @Test
    public void deveEncontrarWalletItemEntreDatas() {
        // Cenário
        Wallet wallet = new Wallet();
        wallet.setName("Carteira 1");
        wallet.setValue(BigDecimal.valueOf(500));

        WalletItem walletItem = new WalletItem(wallet, DATA, TYPE, DESCRIPTION, VALUE);

        LocalDate currentDate = LocalDate.now();
        LocalDate currentDatePlus5Days = currentDate.plusDays(5);
        LocalDate currentDatePlus7Days = currentDate.plusDays(7);

        WalletItem w1 = new WalletItem();
        w1.setWallet(walletItem.getWallet());
        w1.setDate(currentDatePlus5Days);
        w1.setType(TYPE);
        w1.setDescription(DESCRIPTION);
        w1.setValue(VALUE);

        WalletItem w2 = new WalletItem();
        w2.setWallet(walletItem.getWallet());
        w2.setDate(currentDatePlus7Days);
        w2.setType(TYPE);
        w2.setDescription(DESCRIPTION);
        w2.setValue(VALUE);

        this.walletItemRepository.saveAll(List.of(w1, w2));

        // Ação
        Page<WalletItem> response = this.walletItemRepository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(wallet.getId(), DATA, currentDatePlus7Days, PageRequest.of(0, 10));

        // Verificação
        assertEquals(response.getContent().size(), 2);
        assertEquals(response.getTotalElements(), 2);
        assertEquals(response.getContent().get(0).getWallet().getId(), wallet.getId());
    }

    @Test
    public void deveEncontrarWalletItemPeloTipoDeCarteira() {
        // Cenário
        Wallet wallet = new Wallet();
        wallet.setName("Carteira 1");
        wallet.setValue(BigDecimal.valueOf(500));

        WalletItem walletItem = new WalletItem(wallet, DATA, TYPE, DESCRIPTION, VALUE);
        this.walletItemRepository.save(walletItem);

        // Ação
        List<WalletItem> response = this.walletItemRepository.findByWalletIdAndType(walletItem.getWallet().getId(), TYPE);

        // Verificação
        assertEquals(response.size(), 1);
        assertEquals(response.get(0).getType(), TYPE);
    }

    @Test
    public void deveSomarOValorDeTodasAsWalletPeloWalletId() {
        // Cenário
        Wallet wallet = new Wallet();
        wallet.setName("Carteira 1");
        wallet.setValue(BigDecimal.valueOf(500));

        WalletItem walletItem = new WalletItem(wallet, DATA, TYPE, DESCRIPTION, VALUE);

        WalletItem newWalletItem = new WalletItem(walletItem.getWallet(), DATA, TYPE, DESCRIPTION, BigDecimal.valueOf(150.80));
        this.walletItemRepository.saveAll(List.of(walletItem, newWalletItem));

        // Ação
        BigDecimal response = this.walletItemRepository.sumByWalletId(newWalletItem.getWallet().getId());

        // Verificação
        assertEquals(response.compareTo(BigDecimal.valueOf(215.80)), 0);
    }
}
