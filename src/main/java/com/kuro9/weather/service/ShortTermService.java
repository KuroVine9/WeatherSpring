package com.kuro9.weather.service;

import com.kuro9.weather.dataclass.ShortTermDto;
import com.kuro9.weather.dataclass.apicall.ShortApiResponse;
import com.kuro9.weather.dataclass.apicall.ShortTermCallData;
import com.kuro9.weather.service.apicall.KmaApiClient;
import com.kuro9.weather.service.apicall.KmaApiInterface;
import com.kuro9.weather.service.interfaces.ShortTermInterface;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;


/**
 * 기상청 api와 연결
 */
@Service
public class ShortTermService extends ShortTermInterface {
    private final KmaApiInterface api;

    public ShortTermService(KmaApiInterface api) {
        this.api = api;
    }

    @Override
    public ShortTermDto readShortTermLog(int nx, int ny, int hourOffset) throws NoSuchElementException, SocketTimeoutException {
        String baseTime = getBaseTime(), baseDate = getBaseDate(), fcstTime = getOffsetTime(hourOffset), fcstDate = getOffsetDate(hourOffset);
        ArrayList<ShortTermCallData> filteredData = new ArrayList<>();

        ShortApiResponse response = api.shortTermCall(nx, ny, baseDate, baseTime);
        if (response.response.body.items.item.isEmpty()) throw new NoSuchElementException();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime offsetTime = LocalDateTime.parse(fcstDate + fcstTime, formatter);

        int dataCount = response.response.body.totalCount;

        loop:
        for (int i = 0; i < dataCount / 100; i++) {

            for (var data : response.response.body.items.item) {
                LocalDateTime time = LocalDateTime.parse(data.getFcstDate() + data.getFcstTime(), formatter);

                if (time.isEqual(offsetTime)) {
                    filteredData.add(data);
                }
                else if (time.isAfter(offsetTime)) {
                    break loop;
                }
            }
            if (i + 1 == dataCount / 100) break;

            response = api.shortTermCall(
                    (new KmaApiClient.ShortParamBuilder(baseDate, baseTime, nx, ny))
                            .numOfRows(100)
                            .pageNo(i + 1)
                            .build()
            );
        }

        if (filteredData.isEmpty()) throw new NoSuchElementException();

        return new ShortTermDto(baseDate, baseTime, nx, ny, filteredData.stream().map(item ->
                new ShortTermDto.ShortTermCategory(
                        item.getCategory(),
                        item.getFcstDate(),
                        item.getFcstTime(),
                        item.getFcstValue()
                )).toList());
    }

}
