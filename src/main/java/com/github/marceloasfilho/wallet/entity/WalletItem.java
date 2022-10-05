package com.github.marceloasfilho.wallet.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;
    @NotNull
    private LocalDate date;
    @NotNull
    private String type;
    @NotNull
    private String description;
    @NotNull
    private BigDecimal value;


    public WalletItem(Wallet wallet, LocalDate date, String type, String description, BigDecimal value) {
        this.wallet = wallet;
        this.date = date;
        this.type = type;
        this.description = description;
        this.value = value;
    }
}
