package com.github.marceloasfilho.wallet.service;

import com.github.marceloasfilho.wallet.entity.User;
import com.github.marceloasfilho.wallet.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void deveEncontrarUmUsuarioPorEmail() {
        // Cenário
        User user = new User();
        user.setName("Marcelo Filho");
        user.setEmail("marceloandradesilvafilho@gmail.com");
        user.setPassword("abcd1234");

        when(this.userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        // Ação
        Optional<User> UserByEmail = this.userService.findByEmail(anyString());

        // Verificação
        Assert.assertTrue(UserByEmail.isPresent());
    }
}
