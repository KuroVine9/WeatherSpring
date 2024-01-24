package com.kuro9.weather;

import com.kuro9.weather.entity.UltraTerm;
import com.kuro9.weather.entity.id.UltraTermPK;
import com.kuro9.weather.repository.UltraTermRepository;
import com.kuro9.weather.service.UltraTermCacheProxy;
import com.kuro9.weather.service.UltraTermService;
import com.kuro9.weather.service.interfaces.UltraTermInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

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
        assertThrows(NoSuchElementException.class, () -> ultra.readUltraTermLog(0, 0));
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
        String baseDate = ReflectionTestUtils.invokeMethod(ultraTermCacheProxy, "getBaseDate");
        String baseTime = ReflectionTestUtils.invokeMethod(ultraTermCacheProxy, "getBaseTime");
        try {
            UltraTerm data = new UltraTerm(
                    new UltraTermPK(baseDate, baseTime, "TST", 0, 0),
                    "20021024", "0000", "test"
            );
            repo.save(data);


            var output = testObj.readUltraTermLog(0, 0);

            assertFalse(output.getItems().isEmpty());
            assertEquals("TST", output.getItems().get(0).getCategory());
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            repo.deleteById(new UltraTermPK(baseDate, baseTime, "TST", 0, 0));
        }

        assertTrue(result);
    }
}
