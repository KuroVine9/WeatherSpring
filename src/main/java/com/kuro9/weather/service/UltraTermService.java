package com.kuro9.weather.service;

import com.kuro9.weather.dataclass.UltraTermDto;
import com.kuro9.weather.dataclass.apicall.UltraApiResponse;
import com.kuro9.weather.dataclass.apicall.UltraTermCallData;
import com.kuro9.weather.service.apicall.KmaApiInterface;
import com.kuro9.weather.service.interfaces.UltraTermInterface;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UltraTermService extends UltraTermInterface {

    private final KmaApiInterface api;

    public UltraTermService(KmaApiInterface api) {
        this.api = api;
    }

    @Override
    public UltraTermDto readUltraTermLog(int nx, int ny) throws NoSuchElementException, IllegalArgumentException, SocketTimeoutException {
        String baseTime = getBaseTime(), baseDate = getBaseDate();
        UltraApiResponse response = api.ultraTermCall(nx, ny, baseDate, baseTime);

        if (response.response.body.items.item.isEmpty()) throw new NoSuchElementException();
        List<UltraTermCallData> data = response.response.body.items.item; // 카테고리*예보 개수 = 60개 상정

        return new UltraTermDto(
                baseDate, baseTime,
                nx, ny,
                data.stream().map(item -> new UltraTermDto.UltraTermCategory(
                        item.getCategory(),
                        item.getFcstDate(),
                        item.getFcstTime(),
                        item.getFcstValue()
                )).toList()
        );
    }
}
