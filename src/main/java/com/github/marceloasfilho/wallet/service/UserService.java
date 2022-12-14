package com.github.marceloasfilho.wallet.service;

import com.github.marceloasfilho.wallet.entity.User;

import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> findByEmail(String email);
}
