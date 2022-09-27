package com.github.marceloasfilho.wallet.dto;

import com.github.marceloasfilho.wallet.entity.User;
import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String email;
    private String password;

    public User toModel() {
        User user = new User();

        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);

        return user;
    }
}
