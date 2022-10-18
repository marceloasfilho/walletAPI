package com.github.marceloasfilho.wallet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.marceloasfilho.wallet.dto.WalletItemDTO;
import com.github.marceloasfilho.wallet.entity.Wallet;
import com.github.marceloasfilho.wallet.entity.WalletItem;
import com.github.marceloasfilho.wallet.enums.WalletItemTypeEnum;
import com.github.marceloasfilho.wallet.service.WalletItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
public class WalletItemControllerTest {
    @MockBean
    private WalletItemService walletItemService;
    @Autowired
    private MockMvc mockMvc;
    private static final Long ID = 1L;
    private static final LocalDate DATE = LocalDate.now();
    private static final WalletItemTypeEnum TYPE = WalletItemTypeEnum.INPUT;
    private static final String DESCRIPTION = "Conta de Energia";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);
    private static final String URL = "/wallet-item";
    private WalletItem walletItem;

    @Before
    public void setup() {
        Wallet wallet = new Wallet();
        wallet.setId(ID);
        wallet.setName("Carteira 1");
        wallet.setValue(BigDecimal.valueOf(500));
        this.walletItem = new WalletItem(wallet, DATE, TYPE, DESCRIPTION, VALUE);
    }

    @Test
    public void deveSalvarWalletItem() throws Exception {
        // Mock
        when(this.walletItemService.save(any(WalletItem.class))).thenReturn(this.walletItem);
        // Ação + Verificação
        mockMvc
                .perform(post(URL.concat("/save")).content(this.getJsonPayload())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.wallet").value(ID))
                .andExpect(jsonPath("$.data.date").value(this.getLocalDateFormatted(DATE)))
                .andExpect(jsonPath("$.data.description").value(DESCRIPTION))
                .andExpect(jsonPath("$.data.type").value(TYPE.toString()))
                .andExpect(jsonPath("$.data.value").value(VALUE))
                .andExpect(jsonPath("$.data.wallet").value(ID));


    }

    private String getJsonPayload() throws JsonProcessingException {
        WalletItemDTO walletItemDTO = new WalletItemDTO();
        walletItemDTO.setWallet(ID);
        walletItemDTO.setDate(DATE);
        walletItemDTO.setDescription(DESCRIPTION);
        walletItemDTO.setType(TYPE.toString());
        walletItemDTO.setValue(VALUE);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(walletItemDTO);
    }

    private String getLocalDateFormatted(LocalDate localDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTimeFormatter.format(localDate);
    }
}
