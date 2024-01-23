package com.kuro9.weather.service.apicall;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kuro9.weather.dataclass.apicall.ApiErrorData;
import com.kuro9.weather.dataclass.apicall.ApiResponse;
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
     * {@link RuntimeException}: api키 만료 등 치명적인 오류 시 throw
     *
     * @param str  json 스트링
     * @param type 타깃 객체
     * @throws SocketTimeoutException 일시적으로 기상청 api 사용 불가할 때
     */
    private <T> T toJson(String str, Class<T> type) throws SocketTimeoutException {
        try {
            return (new ObjectMapper()).readValue(str, type);
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

            switch (error.getCmmMsgHeader().getReturnReasonCode()) {
                case 3 -> throw new NoSuchElementException();
                case 10, 11 -> throw new IllegalArgumentException();
                case 21, 22, 30 -> throw new SocketTimeoutException(); // 이상하게 간헐적으로 30이 발생하는 경우가 많아서 일단 일시적 오류로 처리
                default -> throw new RuntimeException("기상청 api 에러 코드 " + error.getCmmMsgHeader().getReturnReasonCode());
            }
        }
    }

    @Override
    public ApiResponse midTermCall(String regId, String tmFc) throws SocketTimeoutException {
        return toJson(api.midTermCall(regId, tmFc), ApiResponse.class);
    }

    @Override
    public ApiResponse midTermCall(Map<String, String> param) throws SocketTimeoutException {
        return toJson(api.midTermCall(param), ApiResponse.class);
    }
}
