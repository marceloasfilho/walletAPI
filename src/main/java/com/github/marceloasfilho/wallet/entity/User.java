package com.github.marceloasfilho.wallet.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "THE FIELD 'name' is mandatory")
    private String name;
    @NotBlank(message = "THE FIELD 'email' is mandatory")
    @Email
    private String email;
    @NotBlank(message = "THE FIELD 'password' is mandatory")
    private String password;
}
