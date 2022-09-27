package com.github.marceloasfilho.wallet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldTests {
    @Test
    public void helloWorld() {
        assertEquals(1, 1);
    }
}
