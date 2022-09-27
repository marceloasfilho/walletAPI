package com.github.marceloasfilho.wallet.controller;

import com.github.marceloasfilho.wallet.dto.UserDTO;
import com.github.marceloasfilho.wallet.entity.User;
import com.github.marceloasfilho.wallet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/save")
    public ResponseEntity<User> saveUser(@Valid @RequestBody UserDTO userDTO) {
        User save = this.userService.save(userDTO.toModel());
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }
}
