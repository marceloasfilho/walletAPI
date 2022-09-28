package com.github.marceloasfilho.wallet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.marceloasfilho.wallet.dto.UserDTO;
import com.github.marceloasfilho.wallet.entity.User;
import com.github.marceloasfilho.wallet.service.UserService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final String NAME = "Gretchen";
    private static final String EMAIL = "grethe3105@uorak.com";
    private static final String PASSWORD = "12345";
    private static final String URL = "/user/save";

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveRetornarCreatedAoSalvarUmUsuarioValido() throws Exception {

        when(this.userService.save(any(User.class))).thenReturn(this.getMockUser());

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post(URL)
                                .content(this.getPayload(NAME, EMAIL, PASSWORD))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    public void deveRetornarBadRequestaAoSalvarUsuarioComEmailInvalido() throws Exception {
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post(URL)
                                .content(this.getPayload(NAME, "email", PASSWORD))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deveRetornarBadRequestaAoSalvarUsuarioComEmailEmBranco() throws Exception {
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post(URL)
                                .content(this.getPayload(NAME, "", PASSWORD))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deveRetornarBadRequestaAoSalvarUsuarioComNomeEmBranco() throws Exception {
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post(URL)
                                .content(this.getPayload("", EMAIL, PASSWORD))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deveRetornarBadRequestaAoSalvarUsuarioComSenhaEmBranco() throws Exception {
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post(URL)
                                .content(this.getPayload(NAME, EMAIL, ""))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private String getPayload(String name, String email, String password) throws JsonProcessingException {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(name);
        userDTO.setEmail(email);
        userDTO.setPassword(password);

        User user = userDTO.toModel();

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(user);
    }

    private User getMockUser() {
        User user = new User();
        user.setName(NAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        return user;
    }
}
