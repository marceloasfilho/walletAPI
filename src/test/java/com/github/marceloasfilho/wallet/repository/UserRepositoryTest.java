package com.github.marceloasfilho.wallet.repository;

import com.github.marceloasfilho.wallet.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void deveSalvarUmUsuario() {

        //Cenário
        User user = new User();
        user.setName("Marcelo Filho");
        user.setEmail("marceloandradesilvafilho@gmail.com");
        user.setPassword("abcd1234");

        // Ação
        User savedUser = this.userRepository.save(user);

        // Verificação
        Assert.assertNotNull(savedUser);
    }

    @Test
    public void deveEncontrarUmUsuarioPorEmail() {
        // Cenário
        User user = new User();
        user.setName("Marcelo Filho");
        user.setEmail("marceloandradesilvafilho@gmail.com");
        user.setPassword("abcd1234");

        // Ação
        this.userRepository.save(user);
        Optional<User> userByEmail = this.userRepository.findByEmail(user.getEmail());

        // Verificação
        Assert.assertTrue(userByEmail.isPresent());
        Assert.assertEquals(user.getEmail(), userByEmail.get().getEmail());
    }
}