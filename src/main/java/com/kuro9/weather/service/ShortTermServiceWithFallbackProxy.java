package com.kuro9.weather.service;

import com.kuro9.weather.dataclass.ShortTermDto;
import com.kuro9.weather.dataclass.apicall.ShortApiResponse;
import com.kuro9.weather.dataclass.apicall.ShortTermCallData;
import com.kuro9.weather.service.apicall.KmaApiInterface;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

@Primary
@Service
public class ShortTermServiceWithFallbackProxy extends ShortTermInterface {

    private final ShortTermService shortTermService;
    private final KmaApiInterface api;

    public ShortTermServiceWithFallbackProxy(ShortTermService shortTermService, KmaApiInterface api) {
        this.shortTermService = shortTermService;
        this.api = api;
    }

    @Override
    public ShortTermDto readShortTermLog(int nx, int ny, int hourOffset) throws NoSuchElementException, IllegalArgumentException, SocketTimeoutException {
        String baseTime = getBaseTime(), baseDate = getBaseDate(), fcstTime = getOffsetTime(hourOffset), fcstDate = getOffsetDate(hourOffset);
        try {
            return shortTermService.readShortTermLog(nx, ny, hourOffset);
        }
        catch (NoSuchElementException e) {
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

                int finalI = i;
                HashMap<String, String> param = new HashMap<>() {{
                    put("numOfRows", "100");
                    put("pageNo", Integer.toString(finalI + 1));
                    put("base_date", baseDate);
                    put("base_time", baseTime);
                    put("nx", Integer.toString(nx));
                    put("ny", Integer.toString(ny));
                }};
                response = api.shortTermCall(param);

            }

            if (filteredData.isEmpty()) throw new NoSuchElementException();

            var result = new ShortTermDto(baseDate, baseTime, nx, ny, filteredData.stream().map(item ->
                    new ShortTermDto.ShortTermCategory(
                            item.getCategory(),
                            item.getFcstDate(),
                            item.getFcstTime(),
                            item.getFcstValue()
                    )).toList());
            shortTermService.storeShortTermData(result);
            return result;
        }
    }
}
