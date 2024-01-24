package com.kuro9.weather.service.apicall;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kuro9.weather.dataclass.apicall.ApiErrorData;
import com.kuro9.weather.dataclass.apicall.HeaderInterface;
import com.kuro9.weather.dataclass.apicall.MidApiResponse;
import com.kuro9.weather.dataclass.apicall.ShortApiResponse;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class KmaApiImpl implements KmaApiInterface {
    private final KmaApiClient api;

    public KmaApiImpl(KmaApiClient api) {
        this.api = api;
    }

    /**
     * @param str  json 스트링
     * @param type 타깃 객체
     * @throws SocketTimeoutException 일시적으로 기상청 api 사용 불가할 때
     */
    private <T extends HeaderInterface> T toJson(String str, Class<T> type) throws SocketTimeoutException, NoSuchElementException, IllegalArgumentException, UnknownError {
        try {
            T result = (new ObjectMapper()).readValue(str, type);
            errorSwitch(result.getResultCode());
            return result;
        }
        catch (Exception e) {
            ApiErrorData error;
            try {
                error = (new XmlMapper()).readValue(str, ApiErrorData.class);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException("파싱 실패");
            }

            errorSwitch(error.getCmmMsgHeader().getReturnReasonCode());

            throw new IllegalStateException("UnHandled Error or something");
        }
    }

    private void errorSwitch(int code) throws SocketTimeoutException, NoSuchElementException, IllegalArgumentException, UnknownError {
        switch (code) {
            case 0 -> {
            }
            case 3 -> throw new NoSuchElementException();
            case 10, 11 -> throw new IllegalArgumentException();
            case 21, 22 -> throw new SocketTimeoutException(Integer.toString(code));
            default -> throw new UnknownError(Integer.toString(code));
        }
    }

    @Override
    public MidApiResponse midTermCall(String regId, String tmFc) throws SocketTimeoutException {
        return toJson(api.midTermCall(regId, tmFc), MidApiResponse.class);
    }

    @Override
    public MidApiResponse midTermCall(Map<String, String> param) throws SocketTimeoutException {
        return toJson(api.midTermCall(param), MidApiResponse.class);
    }

    @Override
    public ShortApiResponse shortTermCall(int nx, int ny, String base_date, String base_time) throws SocketTimeoutException {
        return toJson(api.shortTermCall(nx, ny, base_date, base_time), ShortApiResponse.class);
    }

    @Override
    public ShortApiResponse shortTermCall(Map<String, String> param) throws SocketTimeoutException {
        return toJson(api.shortTermCall(param), ShortApiResponse.class);
    }
}
