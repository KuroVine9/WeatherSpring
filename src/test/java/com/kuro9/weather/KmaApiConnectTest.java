package com.kuro9.weather;

import com.kuro9.weather.service.apicall.KmaApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class KmaApiConnectTest {

    @Autowired
    private KmaApiClient api;

    @Test
    public void midTerm() {
        Supplier<String> getTime = () -> {
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
        };
        boolean result = false;
        try {
            System.out.println(api.midTermCall("11B00000", getTime.get()));
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
        Supplier<String> getBaseTime = () -> {
            LocalDateTime currentDateTime = LocalDateTime.now();

            int hour = currentDateTime.getHour();
            int minute = currentDateTime.getMinute();
            LocalDateTime closestTime = currentDateTime.minusDays(1).withHour(23).withMinute(0).withSecond(0).withNano(0);

            for (int i = 2; i < 24; i += 3) {
                if (hour < i || (hour == i && minute <= 10))
                    break;

                closestTime = closestTime.plusHours(3);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
            return closestTime.format(formatter);
        };
        Supplier<String> getBaseDate = () -> {
            LocalDateTime currentDateTime = LocalDateTime.now();

            int hour = currentDateTime.getHour();
            int minute = currentDateTime.getMinute();
            LocalDateTime closestTime = currentDateTime;

            if (hour < 2 || (hour == 2 && minute <= 10)) {
                closestTime = currentDateTime.minusDays(1).withHour(23).withMinute(0).withSecond(0).withNano(0);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            return closestTime.format(formatter);
        };
        System.out.println("date: " + getBaseDate.get() + "\ttime: " + getBaseTime.get());
        try {
            System.out.println(api.shortTermCall(55, 127, getBaseDate.get(), getBaseTime.get()));
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result);
    }
}
