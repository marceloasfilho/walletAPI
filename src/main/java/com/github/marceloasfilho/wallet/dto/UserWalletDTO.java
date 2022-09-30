package com.github.marceloasfilho.wallet.dto;

import com.github.marceloasfilho.wallet.entity.User;
import com.github.marceloasfilho.wallet.entity.UserWallet;
import com.github.marceloasfilho.wallet.entity.Wallet;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserWalletDTO {
    @NotNull(message = "Informe o id do usuário")
    private Long users;
    @NotNull(message = "Informe o id da carteira")
    private Long wallet;

    public UserWallet toModel() {
        UserWallet userWallet = new UserWallet();

        User user = new User();
        user.setId(this.users);

        Wallet wallet = new Wallet();
        wallet.setId(this.wallet);

        userWallet.setUser(user);
        userWallet.setWallet(wallet);

        return userWallet;
    }
}
