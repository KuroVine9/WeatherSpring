package com.kuro9.weather;

import com.kuro9.weather.repository.ShortTermRepository;
import com.kuro9.weather.repository.UltraTermRepository;
import com.kuro9.weather.service.ShortTermCacheProxy;
import com.kuro9.weather.service.UltraTermCacheProxy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.SocketTimeoutException;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class RepoQueryTest {

    @Autowired
    private ShortTermRepository shortRepo;
    @Autowired
    private UltraTermRepository ultraRepo;
    @Autowired
    private ShortTermCacheProxy shortService;
    @Autowired
    private UltraTermCacheProxy ultraService;

    @Test
    public void shortTermRepo() throws SocketTimeoutException {
        int nx = 55, ny = 127, hourOffset = 3;
        String baseDate = ReflectionTestUtils.invokeMethod(shortService, "getBaseDate");
        String baseTime = ReflectionTestUtils.invokeMethod(shortService, "getBaseTime");
        String offsetDate = ReflectionTestUtils.invokeMethod(shortService, "getOffsetDate", hourOffset);
        String offsetTime = ReflectionTestUtils.invokeMethod(shortService, "getOffsetTime", hourOffset);
        shortService.readShortTermLog(nx, ny, hourOffset);

        var result =
                shortRepo.findAllByIdBaseDateAndIdBaseTimeAndIdNxAndIdNyAndFcstDateAndFcstTime(baseDate, baseTime, nx, ny, offsetDate, offsetTime);

        assertFalse(result.isEmpty());
    }

    @Test
    public void ultraTermRepo() throws SocketTimeoutException {
        int nx = 55, ny = 12;
        String baseDate = ReflectionTestUtils.invokeMethod(ultraService, "getBaseDate");
        String baseTime = ReflectionTestUtils.invokeMethod(ultraService, "getBaseTime");
        ultraService.readUltraTermLog(nx, ny);

        var result =
                ultraRepo.findAllByIdBaseDateAndIdBaseTimeAndIdNxAndIdNy(baseDate, baseTime, nx, ny);

        assertFalse(result.isEmpty());
    }

}
