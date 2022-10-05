package com.github.marceloasfilho.wallet.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class WalletItemDTO {
    @NotNull(message = "Insira um id de wallet")
    private Long wallet;
    @NotNull(message = "Insira uma data")
    private LocalDate date;
    @NotNull(message = "Informe um tipo")
    private String type;
    @NotNull(message = "Informe uma descrição")
    @Length(min = 5, message = "A descrição deve ter no mínimo 5 caracteres")
    private String description;
    @NotNull(message = "Informe um valor")
    private BigDecimal value;
}
