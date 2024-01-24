package com.kuro9.weather.service.interfaces;

import com.kuro9.weather.dataclass.UltraTermDto;

import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

public abstract class UltraTermInterface {

    public abstract UltraTermDto readUltraTermLog(int nx, int ny) throws NoSuchElementException, IllegalArgumentException, SocketTimeoutException;

    protected String getBaseDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return currentDateTime.format(formatter);
    }

    protected String getBaseTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime targetTime = currentDateTime.getMinute() <= 45 ?
                currentDateTime.minusHours(1).withMinute(30).withSecond(0).withNano(0) :
                currentDateTime.withMinute(30).withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        return targetTime.format(formatter);
    }
}
