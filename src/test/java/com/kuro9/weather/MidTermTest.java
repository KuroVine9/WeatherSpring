package com.kuro9.weather;

import com.kuro9.weather.service.MidTermInterface;
import com.kuro9.weather.service.apicall.KmaApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MidTermTest {
    private final String testRegionId = "11B00000";
    private final String midTermTime = getMidTermTime();
    @Autowired
    private MidTermInterface midTermService;
    @Autowired
    private KmaApiClient apiClient;

    public String getMidTermTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        int hour = currentDateTime.getHour();
        LocalDateTime closestTime;

        if (hour < 6) {
            closestTime = currentDateTime.minusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0);
        }
        else if (hour < 18) {
            closestTime = LocalDateTime.of(currentDateTime.toLocalDate(), java.time.LocalTime.of(6, 0));
        }
        else {
            closestTime = LocalDateTime.of(currentDateTime.toLocalDate(), java.time.LocalTime.of(18, 0));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return closestTime.format(formatter);
    }

    @BeforeTestMethod
    public void beforeTest() {
        // 초기 api 콜 안하면 api가 30 에러 내고 시작하기 때문에 삽입
        try {
            apiClient.midTermCall(testRegionId, midTermTime);
        }
        catch (Exception ignored) {
        }
    }

    @Test
    public void rawApiMidTerm() {
        boolean result = false;
        try {
            System.out.println(apiClient.midTermCall(testRegionId, midTermTime));
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result);
    }

    @Test
    public void serviceMidTerm() {
        boolean result = false;
        try {
            System.out.println(midTermService.readMidTermLog(testRegionId));
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result);
    }
}
