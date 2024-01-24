package com.kuro9.weather.service.interfaces;

import com.kuro9.weather.dataclass.ShortTermDto;

import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

public abstract class ShortTermInterface {

    public abstract ShortTermDto readShortTermLog(int nx, int ny, int hourOffset) throws NoSuchElementException, IllegalArgumentException, SocketTimeoutException;


    protected String getBaseDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        int hour = currentDateTime.getHour();
        int minute = currentDateTime.getMinute();
        LocalDateTime closestTime = currentDateTime;

        if (hour < 2 || (hour == 2 && minute <= 10)) {
            closestTime = currentDateTime.minusDays(1).withHour(23).withMinute(0).withSecond(0).withNano(0);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return closestTime.format(formatter);
    }

    protected String getBaseTime() {
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
    }

    protected String getOffsetDate(int offsetHour) {
        var time = LocalDateTime.now().plusHours(offsetHour).withMinute(0).withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return time.format(formatter);
    }

    protected String getOffsetTime(int offsetHour) {
        var time = LocalDateTime.now().plusHours(offsetHour).withMinute(0).withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        return time.format(formatter);
    }
}
