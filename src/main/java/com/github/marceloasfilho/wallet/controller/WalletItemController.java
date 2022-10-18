package com.github.marceloasfilho.wallet.controller;

import com.github.marceloasfilho.wallet.dto.WalletItemDTO;
import com.github.marceloasfilho.wallet.entity.Wallet;
import com.github.marceloasfilho.wallet.entity.WalletItem;
import com.github.marceloasfilho.wallet.enums.WalletItemTypeEnum;
import com.github.marceloasfilho.wallet.response.Response;
import com.github.marceloasfilho.wallet.service.WalletItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/wallet-item")
@AllArgsConstructor
public class WalletItemController {
    private final WalletItemService walletItemService;

    @Transactional
    @PostMapping(path = "/save")
    public ResponseEntity<Response<WalletItemDTO>> save(@Valid @RequestBody WalletItemDTO walletItemDTO, BindingResult bindingResult) {
        Response<WalletItemDTO> response = new Response<>();
        response.setData(walletItemDTO);

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        this.walletItemService.save(walletItemDTO.toModel());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{wallet}")
    public ResponseEntity<Response<Page<WalletItemDTO>>> findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(
            @PathVariable("wallet") Long wallet,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate,
            @RequestParam(name = "page", defaultValue = "0") int page) {

        Response<Page<WalletItemDTO>> response = new Response<>();

        Page<WalletItem> walletItems = this.walletItemService.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(wallet, startDate, endDate, page);
        Page<WalletItemDTO> walletItemDTOS = walletItems.map(this::convertEntityToDTO);

        response.setData(walletItemDTOS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/type/{wallet}")
    public ResponseEntity<Response<List<WalletItemDTO>>> findByWalletIdAndType(
            @PathVariable("wallet") Long wallet,
            @RequestParam("type") String type) {

        Response<List<WalletItemDTO>> response = new Response<>();

        List<WalletItem> walletItems = this.walletItemService.findByWalletIdAndType(wallet, WalletItemTypeEnum.valueOf(type));
        List<WalletItemDTO> walletItemDTOS = walletItems.stream().map(this::convertEntityToDTO).toList();

        response.setData(walletItemDTOS);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/sum/{wallet}")
    public ResponseEntity<Response<BigDecimal>> sumByWalletId(@PathVariable("wallet") Long wallet) {
        Response<BigDecimal> response = new Response<>();

        BigDecimal sumByWalletId = this.walletItemService.sumByWalletId(wallet);
        BigDecimal sum = sumByWalletId == null ? BigDecimal.ZERO : sumByWalletId;
        response.setData(sum);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Response<WalletItemDTO>> update(@Valid @RequestBody WalletItemDTO walletItemDTO, BindingResult bindingResult) {
        Response<WalletItemDTO> response = new Response<>();

        Optional<WalletItem> walletItem = this.walletItemService.findById(walletItemDTO.getWallet());

        if (walletItem.isEmpty()) {
            bindingResult.addError(new ObjectError("Wallet", "WalletItem não encontrado"));
        } else {
            if (walletItem.get().getWallet().getId().compareTo(walletItemDTO.getWallet()) != 0) {
                bindingResult.addError(new ObjectError("WalletItemChanged", "Você não pode alterar a carteira"));
            }
        }

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        WalletItem saved = this.walletItemService.save(this.convertDTOtoEntity(walletItemDTO));
        response.setData(this.convertEntityToDTO(saved));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{wallet}")
    public ResponseEntity<Response<String>> delete(@PathVariable("wallet") Long wallet) {
        Response<String> response = new Response<>();

        Optional<WalletItem> walletItem = this.walletItemService.findById(wallet);

        if (walletItem.isEmpty()) {
            response.getErrors().add("Carteira de id " + wallet + " não encontrada!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        this.walletItemService.deleteById(wallet);
        response.setData("Carteira de id " + wallet + " deletada com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private WalletItemDTO convertEntityToDTO(WalletItem walletItem) {
        WalletItemDTO walletItemDTO = new WalletItemDTO();

        walletItemDTO.setWallet(walletItem.getWallet().getId());
        walletItemDTO.setDate(walletItem.getDate());
        walletItemDTO.setDescription(walletItem.getDescription());
        walletItemDTO.setType(walletItemDTO.getType());
        walletItemDTO.setValue(walletItem.getValue());
        return walletItemDTO;
    }

    private WalletItem convertDTOtoEntity(WalletItemDTO walletItemDTO){
        WalletItem walletItem = new WalletItem();
        walletItem.setDate(walletItemDTO.getDate());
        walletItem.setDescription(walletItemDTO.getDescription());
        walletItem.setId(walletItemDTO.getWallet());
        walletItem.setType(WalletItemTypeEnum.valueOf(walletItemDTO.getType()));
        walletItem.setValue(walletItemDTO.getValue());

        Wallet wallet = new Wallet();
        wallet.setId(walletItemDTO.getWallet());

        walletItem.setWallet(wallet);

        return walletItem;
    }
}
