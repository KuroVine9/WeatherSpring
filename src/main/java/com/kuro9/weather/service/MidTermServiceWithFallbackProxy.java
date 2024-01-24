package com.kuro9.weather.service;

import com.kuro9.weather.dataclass.MidTermDto;
import com.kuro9.weather.dataclass.apicall.MidApiResponse;
import com.kuro9.weather.dataclass.apicall.MidTermCallData;
import com.kuro9.weather.service.apicall.KmaApiInterface;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;

/**
 * db에 데이터 존재하지 않으면 api 콜을 통해 정보를 가져오는 클래스
 */
@Primary
@Service
public class MidTermServiceWithFallbackProxy extends MidTermInterface {

    private final MidTermService midTermService;
    private final KmaApiInterface api;

    public MidTermServiceWithFallbackProxy(MidTermService midTermService, KmaApiInterface api) {
        this.midTermService = midTermService;
        this.api = api;
    }

    @Override
    public MidTermDto readMidTermLog(String regId) throws NoSuchElementException, IllegalArgumentException, SocketTimeoutException {
        String tmFc = getTimeCode();
        try {
            return midTermService.readMidTermLog(regId);
        }
        catch (NoSuchElementException e) {
            MidApiResponse apiResponse = api.midTermCall(regId, tmFc);
            if (apiResponse.response.body.items.item.isEmpty()) throw new NoSuchElementException();
            MidTermCallData data = apiResponse.response.body.items.item.get(0);
            var result = new MidTermDto(
                    regId, tmFc,
                    data.toData()
            );
            midTermService.createMidTermLog(result);
            return result;
        }
    }
}
