package com.github.marceloasfilho.wallet.controller;

import com.github.marceloasfilho.wallet.dto.WalletDTO;
import com.github.marceloasfilho.wallet.response.Response;
import com.github.marceloasfilho.wallet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @Transactional
    @RequestMapping(path = "/save")
    public ResponseEntity<Response<WalletDTO>> save(@Valid @RequestBody WalletDTO walletDTO, BindingResult bindingResult) {

        Response<WalletDTO> response = new Response<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.setData(walletDTO);
        this.walletService.save(walletDTO.toModel());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
