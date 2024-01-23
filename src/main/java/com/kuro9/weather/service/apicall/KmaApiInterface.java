package com.kuro9.weather.service.apicall;

import com.kuro9.weather.dataclass.apicall.ApiResponse;

import java.net.SocketTimeoutException;
import java.util.Map;

/**
 * Api를 자바 객체로 받기 위한 인터페이스
 */
public interface KmaApiInterface {
    ApiResponse midTermCall(String regId, String tmFc) throws SocketTimeoutException;

    ApiResponse midTermCall(Map<String, String> param) throws SocketTimeoutException;
}
