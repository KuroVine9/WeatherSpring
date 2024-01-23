package com.kuro9.weather.service;

import com.kuro9.weather.dataclass.MidTermDto;

import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

public abstract class MidTermInterface {
    /**
     * @param regId 지역 코드
     */
    public abstract MidTermDto readMidTermLog(String regId) throws NoSuchElementException, IllegalArgumentException, SocketTimeoutException;

    protected String getMidTermTimeCode() {
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
}
