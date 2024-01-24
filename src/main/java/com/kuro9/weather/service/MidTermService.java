package com.kuro9.weather.service;

import com.kuro9.weather.dataclass.MidTermDto;
import com.kuro9.weather.dataclass.apicall.MidApiResponse;
import com.kuro9.weather.dataclass.apicall.MidTermCallData;
import com.kuro9.weather.service.apicall.KmaApiInterface;
import com.kuro9.weather.service.interfaces.MidTermInterface;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;

/**
 * 기상청 api와 연결
 */
@Service
public class MidTermService extends MidTermInterface {
    private final KmaApiInterface api;

    public MidTermService(KmaApiInterface api) {
        this.api = api;
    }

    @Override
    public MidTermDto readMidTermLog(String regId) throws SocketTimeoutException {
        String tmFc = getTimeCode();
        MidApiResponse apiResponse = api.midTermCall(regId, tmFc);

        if (apiResponse.response.body.items.item.isEmpty()) throw new NoSuchElementException();
        MidTermCallData data = apiResponse.response.body.items.item.get(0);

        return new MidTermDto(
                regId, tmFc,
                data.toData()
        );

    }
}
