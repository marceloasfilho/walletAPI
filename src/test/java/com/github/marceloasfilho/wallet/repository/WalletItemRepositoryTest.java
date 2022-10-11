package com.github.marceloasfilho.wallet.repository;

import com.github.marceloasfilho.wallet.entity.Wallet;
import com.github.marceloasfilho.wallet.entity.WalletItem;
import com.github.marceloasfilho.wallet.enums.WalletItemTypeEnum;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class WalletItemRepositoryTest {

    private static final LocalDate DATA = LocalDate.now();
    private static final WalletItemTypeEnum TYPE = WalletItemTypeEnum.INPUT;
    private static final String DESCRIPTION = "CONTA DE LUZ";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);

    private static Long WALLETID = null;
    private static Long WALLETITEMID = null;

    @Before
    public void setup() {
        Wallet wallet = new Wallet();
        wallet.setName("Carteira 1");
        wallet.setValue(BigDecimal.valueOf(500));
        this.walletRepository.save(wallet);

        WalletItem walletItem = new WalletItem(wallet, DATA, TYPE, DESCRIPTION, VALUE);
        this.walletItemRepository.save(walletItem);

        WALLETID = wallet.getId();
        WALLETITEMID = walletItem.getId();
    }

    @After
    public void close() {
        this.walletRepository.deleteAll();
        this.walletItemRepository.deleteAll();
    }

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
        Optional<WalletItem> walletItem = this.walletItemRepository.findById(WALLETITEMID);
        String description = "Descrição Alterada";

        WalletItem changed = walletItem.get();
        changed.setDescription(description);

        // Ação
        this.walletItemRepository.save(changed);

        // Verificação
        Optional<WalletItem> newWalletItem = this.walletItemRepository.findById(WALLETITEMID);
        assertEquals(description, newWalletItem.get().getDescription());
    }

    @Test
    public void deveDeletarWalletItemPeloId() {
        // Cenário
        Optional<WalletItem> walletItem = this.walletItemRepository.findById(WALLETITEMID);
        WalletItem wi = new WalletItem();
        wi.setWallet(walletItem.get().getWallet());
        wi.setDate(DATA);
        wi.setType(TYPE);
        wi.setDescription(DESCRIPTION);
        wi.setValue(VALUE);
        this.walletItemRepository.save(wi);

        // Ação
        this.walletItemRepository.deleteById(wi.getId());

        // Verificação
        Optional<WalletItem> deletedWalletById = this.walletItemRepository.findById(wi.getId());
        assertFalse(deletedWalletById.isPresent());
    }

    @Test
    public void deveEncontrarWalletItemEntreDatas() {
        // Cenário
        Optional<WalletItem> walletItem = this.walletItemRepository.findById(WALLETITEMID);
        LocalDate currentDate = LocalDate.now();
        LocalDate currentDatePlus5Days = currentDate.plusDays(5);
        LocalDate currentDatePlus7Days = currentDate.plusDays(7);

        WalletItem w1 = new WalletItem();
        w1.setWallet(walletItem.get().getWallet());
        w1.setDate(currentDatePlus5Days);
        w1.setType(TYPE);
        w1.setDescription(DESCRIPTION);
        w1.setValue(VALUE);

        WalletItem w2 = new WalletItem();
        w2.setWallet(walletItem.get().getWallet());
        w2.setDate(currentDatePlus7Days);
        w2.setType(TYPE);
        w2.setDescription(DESCRIPTION);
        w2.setValue(VALUE);

        this.walletItemRepository.save(w1);
        this.walletItemRepository.save(w2);

        // Ação
        Page<WalletItem> response = this.walletItemRepository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(WALLETID, DATA, currentDatePlus5Days, PageRequest.of(0, 10));

        // Verificação
        assertEquals(response.getContent().size(), 2);
        assertEquals(response.getTotalElements(), 2);
        assertEquals(response.getContent().get(0).getWallet().getId(), WALLETID);
    }

    @Test
    public void deveEncontrarWalletItemPeloTipoDeCarteira() {
        // Cenário

        // Ação
        List<WalletItem> response = this.walletItemRepository.findByWalletIdAndType(WALLETID, TYPE);

        // Verificação
        assertEquals(response.size(), 1);
        assertEquals(response.get(0).getType(), TYPE);
    }

    @Test
    public void deveSomarOValorDeTodasAsWalletPeloWalletId(){
        // Cenário
        Optional<WalletItem> walletItem = this.walletItemRepository.findById(WALLETITEMID);
        WalletItem newWalletItem = new WalletItem(walletItem.get().getWallet(), DATA, TYPE, DESCRIPTION, BigDecimal.valueOf(150.80));
        this.walletItemRepository.save(newWalletItem);

        // Ação
        BigDecimal response = this.walletItemRepository.sumByWalletId(WALLETID);

        // Verificação
        assertEquals(response.compareTo(BigDecimal.valueOf(215.80)), 0);
    }
}
