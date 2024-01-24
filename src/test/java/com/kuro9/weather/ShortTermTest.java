package com.kuro9.weather;

import com.kuro9.weather.repository.ShortTermRepository;
import com.kuro9.weather.service.ShortTermCacheProxy;
import com.kuro9.weather.service.ShortTermInterface;
import com.kuro9.weather.service.ShortTermService;
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
    private ShortTermInterface srt;
    @Autowired
    private ShortTermService shortTermService;
    @Autowired
    private ShortTermCacheProxy shortTermCacheProxy;
    @Autowired
    private ShortTermRepository repo;


    @Test
    public void serviceShortTerm() {
        boolean result = false;
        try {
            System.out.println(srt.readShortTermLog(nx, ny, 3));
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
            srt.readShortTermLog(0, 0, 3);
        });
    }

    @Test
    public void apiServiceTest() {
        boolean result = false;
        try {
            System.out.println(shortTermService.readShortTermLog(nx, ny, 3));
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result);
    }

    @Test
    public void dbProxyTest() {
        ShortTermCacheProxy testObj = new ShortTermCacheProxy(null, repo);
        boolean result = false;
        try {
            shortTermCacheProxy.readShortTermLog(nx, ny, 3);

            System.out.println(testObj.readShortTermLog(nx, ny, 3));
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result);
    }
}
