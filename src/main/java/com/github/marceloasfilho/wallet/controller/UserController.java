package com.github.marceloasfilho.wallet.controller;

import com.github.marceloasfilho.wallet.dto.UserDTO;
import com.github.marceloasfilho.wallet.entity.User;
import com.github.marceloasfilho.wallet.response.Response;
import com.github.marceloasfilho.wallet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @Transactional
    @PostMapping(path = "/save")
    public ResponseEntity<Response<UserDTO>> saveUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {

        Response<UserDTO> response = new Response<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user = userDTO.toModel();

        this.userService.save(user);
        userDTO.setPassword(null);
        response.setData(userDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
