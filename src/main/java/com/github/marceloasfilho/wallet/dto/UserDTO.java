package com.github.marceloasfilho.wallet.dto;

import com.github.marceloasfilho.wallet.entity.User;
import com.github.marceloasfilho.wallet.util.Bcrypt;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO {
    @NotNull(message = "Informe o nome de usuário")
    private String name;
    @Email(message = "Informe o email de usuário")
    @NotNull
    private String email;
    @NotNull(message = "Informe a senha do usuário")
    private String password;

    public User toModel() {
        User user = new User();

        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(Bcrypt.getHash(this.password));

        return user;
    }
}
