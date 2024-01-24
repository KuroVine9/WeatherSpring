package com.kuro9.weather;

import com.kuro9.weather.service.ShortTermInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ShortTermTest {
    private final int nx = 55, ny = 127;

    @Autowired
    private ShortTermInterface service;


    @Test
    public void serviceShortTerm() {
        boolean result = false;
        try {
            System.out.println(service.readShortTermLog(nx, ny, 3));
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result);
    }

    @Test
    public void withInvalidPos() {
        assertThrows(NoSuchElementException.class, () -> {
            service.readShortTermLog(0, 0, 3);
        });
    }
}
