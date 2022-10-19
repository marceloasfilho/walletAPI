package com.github.marceloasfilho.wallet.entity;

import com.github.marceloasfilho.wallet.enums.WalletItemTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WalletItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "wallet", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Wallet wallet;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @NotNull
    @Enumerated(EnumType.STRING)
    private WalletItemTypeEnum type;
    @NotNull
    private String description;
    @NotNull
    private BigDecimal value;


    public WalletItem(Wallet wallet, LocalDate date, WalletItemTypeEnum type, String description, BigDecimal value) {
        this.wallet = wallet;
        this.date = date;
        this.type = type;
        this.description = description;
        this.value = value;
    }
}
