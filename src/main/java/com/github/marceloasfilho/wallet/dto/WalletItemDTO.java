package com.github.marceloasfilho.wallet.dto;

import com.github.marceloasfilho.wallet.entity.Wallet;
import com.github.marceloasfilho.wallet.entity.WalletItem;
import com.github.marceloasfilho.wallet.enums.WalletItemTypeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class WalletItemDTO {
    @NotNull(message = "Insira um id de wallet")
    private Long wallet;
    @NotNull(message = "Insira uma data")
    private LocalDate date;
    @NotNull(message = "Informe um tipo")
    @Pattern(regexp = "^(INPUT|OUTPUT)$", message = "Para o tipo somente são aceitos [INPUT] ou [OUTPUT]")
    private String type;
    @NotNull(message = "Informe uma descrição")
    @Length(min = 5, message = "A descrição deve ter no mínimo 5 caracteres")
    private String description;
    @NotNull(message = "Informe um valor")
    private BigDecimal value;

    public WalletItem toModel() {
        Wallet wallet = new Wallet();
        wallet.setId(this.wallet);
        return new WalletItem(wallet, this.date, WalletItemTypeEnum.valueOf(this.type), this.description, this.value);
    }
}
