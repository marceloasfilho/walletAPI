package com.github.marceloasfilho.wallet.dto;

import com.github.marceloasfilho.wallet.entity.Wallet;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class WalletDTO {
    @Length(min = 3, message = "O nome deve conter no mínimo 3 caracteres")
    @NotNull(message = "O nome não deve ser nulo")
    private String name;
    @NotNull(message = "O valor não pode ser nulo")
    private BigDecimal value;

    public Wallet toModel() {
        Wallet wallet = new Wallet();
        wallet.setName(this.name);
        wallet.setValue(this.value);
        return wallet;
    }
}
