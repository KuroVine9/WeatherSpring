package com.kuro9.weather;

import com.kuro9.weather.entity.ShortTerm;
import com.kuro9.weather.entity.id.ShortTermPK;
import com.kuro9.weather.repository.ShortTermRepository;
import com.kuro9.weather.service.ShortTermCacheProxy;
import com.kuro9.weather.service.ShortTermService;
import com.kuro9.weather.service.interfaces.ShortTermInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

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
        assertThrows(NoSuchElementException.class, () -> srt.readShortTermLog(0, 0, 3));
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
        String baseDate = ReflectionTestUtils.invokeMethod(shortTermCacheProxy, "getBaseDate");
        String baseTime = ReflectionTestUtils.invokeMethod(shortTermCacheProxy, "getBaseTime");
        String offsetDate = ReflectionTestUtils.invokeMethod(shortTermCacheProxy, "getOffsetDate", 3);
        String offsetTime = ReflectionTestUtils.invokeMethod(shortTermCacheProxy, "getOffsetTime", 3);
        try {
            ShortTerm data = new ShortTerm(
                    new ShortTermPK(baseDate, baseTime, "TST", 0, 0),
                    offsetDate, offsetTime, "test"
            );
            repo.save(data);


            var output = testObj.readShortTermLog(0, 0, 3);

            assertFalse(output.getItems().isEmpty());
            assertEquals("TST", output.getItems().get(0).getCategory());
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            repo.deleteById(new ShortTermPK(baseDate, baseTime, "TST", 0, 0));
        }

        assertTrue(result);
    }
}
