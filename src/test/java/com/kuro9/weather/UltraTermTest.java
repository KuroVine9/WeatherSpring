package com.kuro9.weather;

import com.kuro9.weather.repository.UltraTermRepository;
import com.kuro9.weather.service.UltraTermCacheProxy;
import com.kuro9.weather.service.UltraTermInterface;
import com.kuro9.weather.service.UltraTermService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UltraTermTest {

    private final int nx = 55, ny = 127;

    @Autowired
    private UltraTermInterface ultra;
    @Autowired
    private UltraTermService ultraTermService;
    @Autowired
    private UltraTermCacheProxy ultraTermCacheProxy;
    @Autowired
    private UltraTermRepository repo;

    @Test
    public void serviceUltraTerm() {
        boolean result = false;
        try {
            System.out.println(ultra.readUltraTermLog(nx, ny));
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
            ultra.readUltraTermLog(0, 0);
        });
    }

    @Test
    public void apiServiceTest() {
        boolean result = false;
        try {
            System.out.println(ultraTermService.readUltraTermLog(nx, ny));
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result);
    }

    @Test
    public void dbProxyTest() {
        UltraTermCacheProxy testObj = new UltraTermCacheProxy(null, repo);
        boolean result = false;
        try {
            ultraTermCacheProxy.readUltraTermLog(nx, ny);

            System.out.println(testObj.readUltraTermLog(nx, ny));
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result);
    }
}
