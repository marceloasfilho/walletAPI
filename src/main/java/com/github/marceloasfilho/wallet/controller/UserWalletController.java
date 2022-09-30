package com.github.marceloasfilho.wallet.controller;

import com.github.marceloasfilho.wallet.dto.UserWalletDTO;
import com.github.marceloasfilho.wallet.entity.UserWallet;
import com.github.marceloasfilho.wallet.response.Response;
import com.github.marceloasfilho.wallet.service.UserWalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/user-wallet")
public class UserWalletController {

    private final UserWalletService userWalletService;


    public UserWalletController(UserWalletService userWalletService) {
        this.userWalletService = userWalletService;
    }

    @PostMapping(path = "/save")
    public ResponseEntity<Response<UserWalletDTO>> save(@Valid @RequestBody UserWalletDTO userWalletDTO, BindingResult bindingResult) {
        Response<UserWalletDTO> response = new Response<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.setData(userWalletDTO);

        UserWallet userWallet = userWalletDTO.toModel();
        this.userWalletService.save(userWallet);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
