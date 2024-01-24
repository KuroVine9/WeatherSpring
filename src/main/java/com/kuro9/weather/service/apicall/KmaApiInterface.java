package com.kuro9.weather.service.apicall;

import com.kuro9.weather.dataclass.apicall.MidApiResponse;
import com.kuro9.weather.dataclass.apicall.ShortApiResponse;
import com.kuro9.weather.dataclass.apicall.UltraApiResponse;

import java.net.SocketTimeoutException;
import java.util.Map;

/**
 * Api를 자바 객체로 받기 위한 인터페이스
 */
public interface KmaApiInterface {
    MidApiResponse midTermCall(String regId, String tmFc) throws SocketTimeoutException;

    MidApiResponse midTermCall(Map<String, String> param) throws SocketTimeoutException;

    ShortApiResponse shortTermCall(int nx, int ny, String base_date, String base_time) throws SocketTimeoutException;

    ShortApiResponse shortTermCall(Map<String, String> param) throws SocketTimeoutException;

    UltraApiResponse ultraTermCall(int nx, int ny, String base_date, String base_time) throws SocketTimeoutException;

    UltraApiResponse ultraTermCall(Map<String, String> param) throws SocketTimeoutException;

}
