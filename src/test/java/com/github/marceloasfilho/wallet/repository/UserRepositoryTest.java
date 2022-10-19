package com.github.marceloasfilho.wallet.repository;

import com.github.marceloasfilho.wallet.entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Before
    public void setup() {
        User user = new User();
        user.setName("Setup User");
        user.setEmail("hermencina4136@uorak.com");
        user.setPassword("Setup Password");
        this.userRepository.save(user);
    }

    @After
    public void close() {
        this.userRepository.deleteAll();
    }

    @Test
    public void deveSalvarUmUsuario() {

        //Cenário
        User user = new User();
        user.setName("Marcelo Filho");
        user.setEmail("marceloandradesilvafilho@gmail.com");
        user.setPassword("abcd1234");

        // Ação
        User save = this.userRepository.save(user);

        // Verificação
        Assert.assertNotNull(save);
    }

    @Test
    public void deveEncontrarUmUsuarioPorEmail() {
        // Cenário
        User user = new User();
        user.setName("Marcelo Filho");
        user.setEmail("hermencina4136@uorak.com");
        user.setPassword("abcd1234");

        // Ação
        Optional<User> userByEmail = this.userRepository.findByEmail(user.getEmail());

        // Verificação
        Assert.assertTrue(userByEmail.isPresent());
        Assert.assertEquals(user.getEmail(), userByEmail.get().getEmail());
    }
}