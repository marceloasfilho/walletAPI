package com.github.marceloasfilho.wallet.repository;

import com.github.marceloasfilho.wallet.entity.WalletItem;
import com.github.marceloasfilho.wallet.enums.WalletItemTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface WalletItemRepository extends JpaRepository<WalletItem, Long> {
    @Query(value = "SELECT * FROM wallet_item WHERE wallet_item.wallet=:wallet AND wallet_item.date BETWEEN :startDate AND :endDate", nativeQuery = true)
    Page<WalletItem> findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(@Param("wallet") Long wallet, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

    List<WalletItem> findByWalletIdAndType(Long walletId, WalletItemTypeEnum type);

    @Query(value = "SELECT SUM(value) FROM wallet_item WHERE wallet_item.wallet=:wallet", nativeQuery = true)
    BigDecimal sumByWalletId(@Param("wallet") Long walletId);
}
