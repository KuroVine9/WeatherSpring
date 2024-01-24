package com.kuro9.weather;

import com.kuro9.weather.service.MidTermInterface;
import com.kuro9.weather.service.ShortTermInterface;
import com.kuro9.weather.service.UltraTermInterface;
import com.kuro9.weather.service.apicall.KmaApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class KmaApiConnectTest {

    @Autowired
    private KmaApiClient api;

    @Autowired
    private UltraTermInterface ultraService;

    @Autowired
    private ShortTermInterface shortService;

    @Autowired
    private MidTermInterface midService;

    @Test
    public void midTerm() {
        String timeCode = ReflectionTestUtils.invokeMethod(midService, "getTimeCode");
        boolean result = false;
        try {
            System.out.println(api.midTermCall("11B00000", timeCode));
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result);
    }

    @Test
    public void shortTerm() {
        boolean result = false;
        String baseTime = ReflectionTestUtils.invokeMethod(shortService, "getBaseTime");
        String baseDate = ReflectionTestUtils.invokeMethod(shortService, "getBaseDate");
        System.out.println("date: " + baseDate + "\ttime: " + baseTime);
        try {
            System.out.println(api.shortTermCall(55, 127, baseDate, baseTime));
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result);
    }

    @Test
    public void ultraTerm() {
        boolean result = false;
        String baseTime = ReflectionTestUtils.invokeMethod(ultraService, "getBaseTime");
        String baseDate = ReflectionTestUtils.invokeMethod(ultraService, "getBaseDate");
        System.out.println("date: " + baseDate + "\ttime: " + baseTime);

        try {
            System.out.println(api.ultraShortTermCall(55, 127, baseDate, baseTime));
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result);
    }
}
