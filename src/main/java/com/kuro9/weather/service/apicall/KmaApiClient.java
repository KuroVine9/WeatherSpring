package com.kuro9.weather.service.apicall;

import com.kuro9.weather.config.WeatherApiConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 기상청 api과의 i/o를 담당하는 클래스
 * 항상 String(응답으로 받은 raw data)을 그대로 반환할 것
 * 가공된 정보가 필요하다면 {@link KmaApiInterface} 사용 바람
 */
@Service
public class KmaApiClient {
    private final WeatherApiConfig config;

    public KmaApiClient(WeatherApiConfig config) {
        this.config = config;
    }

    private String get(String url, Map<String, String> param) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(url);
        WebClient client = WebClient.builder().uriBuilderFactory(factory).build();

        return client.get().uri(uriBuilder -> {
                    param.forEach(uriBuilder::queryParam);
                    return uriBuilder.build();
                }
        ).retrieve().bodyToMono(String.class).block(); // 에러 시 형식 달라지기 때문에 따로 처리
    }

    public String midTermCall(Map<String, String> param) {
        return get(config.getMidBaseUrl(), param);
    }

    public String midTermCall(String regId, String tmFc) {
        HashMap<String, String> param = new HashMap<>() {{
            put("serviceKey", config.getApiKey());
            put("numOfRows", "50");
            put("pageNo", "1");
            put("dataType", "JSON");
            put("regId", regId);
            put("tmFc", tmFc);
        }};

        return midTermCall(param);
    }

    public String shortTermCall(Map<String, String> param) {
        return get(config.getShortBaseUrl() + config.getShortPath(), param);
    }

    public String shortTermCall(int nx, int ny, String base_date, String base_time) {
        HashMap<String, String> param = new HashMap<>() {{
            put("serviceKey", config.getApiKey());
            put("numOfRows", "50");
            put("pageNo", "1");
            put("dataType", "JSON");
            put("base_data", base_date);
            put("base_time", base_time);
            put("nx", Integer.toString(nx));
            put("ny", Integer.toString(ny));
        }};

        return shortTermCall(param);
    }

    public String ultraShortTermCall(Map<String, String> param) {
        return get(config.getShortBaseUrl() + config.getUltraShortPath(), param);
    }
}
