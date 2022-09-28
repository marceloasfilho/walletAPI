package com.github.marceloasfilho.wallet.dto;

import com.github.marceloasfilho.wallet.entity.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDTO {
    @NotBlank(message = "THE FIELD 'name' is mandatory")
    private String name;
    @Email(message = "THE FIELD 'email' is mandatory")
    @NotBlank
    private String email;
    @NotBlank(message = "THE FIELD 'password' is mandatory")
    private String password;

    public User toModel() {
        User user = new User();

        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password.isEmpty() ? null : this.password);

        return user;
    }
}
