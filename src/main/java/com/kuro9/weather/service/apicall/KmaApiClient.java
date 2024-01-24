package com.kuro9.weather.service.apicall;

import com.kuro9.weather.config.WeatherApiConfig;
import lombok.NoArgsConstructor;
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
        param.put("serviceKey", config.getApiKey());
        param.put("dataType", "JSON");
        return get(config.getMidBaseUrl(), param);
    }

    public String midTermCall(String regId, String tmFc) {
        return midTermCall(
                (new MidParamBuilder())
                        .numOfRows(50)
                        .pageNo(1)
                        .regId(regId)
                        .tmFc(tmFc)
                        .build()
        );
    }

    public String shortTermCall(Map<String, String> param) {
        param.put("serviceKey", config.getApiKey());
        param.put("dataType", "JSON");

        return get(config.getShortBaseUrl() + config.getShortPath(), param);
    }

    public String shortTermCall(int nx, int ny, String base_date, String base_time) {
        return shortTermCall(
                (new ShortParamBuilder())
                        .numOfRows(100)
                        .pageNo(1)
                        .base_date(base_date)
                        .base_time(base_time)
                        .nx(nx)
                        .ny(ny)
                        .build()
        );
    }


    public String ultraShortTermCall(Map<String, String> param) {
        param.put("serviceKey", config.getApiKey());
        param.put("dataType", "JSON");
        return get(config.getShortBaseUrl() + config.getUltraShortPath(), param);
    }

    public String ultraShortTermCall(int nx, int ny, String base_date, String base_time) {
        return ultraShortTermCall(
                (new UltraShortParamBuilder())
                        .numOfRows(100)
                        .pageNo(1)
                        .base_date(base_date)
                        .base_time(base_time)
                        .nx(nx)
                        .ny(ny)
                        .build()
        );
    }

    @NoArgsConstructor
    public static class MidParamBuilder {
        private int numOfRows = 50;
        private int pageNo = 1;
        private String regId, tmFc;

        public MidParamBuilder numOfRows(int numOfRows) {
            this.numOfRows = numOfRows;
            return this;
        }

        public MidParamBuilder pageNo(int pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        public MidParamBuilder regId(String regId) {
            this.regId = regId;
            return this;
        }

        public MidParamBuilder tmFc(String tmFc) {
            this.tmFc = tmFc;
            return this;
        }

        public Map<String, String> build() {
            return new HashMap<>() {{
                put("numOfRows", Integer.toString(numOfRows));
                put("pageNo", Integer.toString(pageNo));
                put("regId", regId);
                put("tmFc", tmFc);
            }};
        }
    }

    @NoArgsConstructor
    public static class ShortParamBuilder {
        private int numOfRows = 100;
        private int pageNo = 1;
        private String base_date;
        private String base_time;
        private int nx, ny;

        public ShortParamBuilder numOfRows(int numOfRows) {
            this.numOfRows = numOfRows;
            return this;
        }

        public ShortParamBuilder pageNo(int pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        public ShortParamBuilder base_date(String base_date) {
            this.base_date = base_date;
            return this;
        }

        public ShortParamBuilder base_time(String base_time) {
            this.base_time = base_time;
            return this;
        }

        public ShortParamBuilder nx(int nx) {
            this.nx = nx;
            return this;
        }

        public ShortParamBuilder ny(int ny) {
            this.ny = ny;
            return this;
        }

        public Map<String, String> build() {
            return new HashMap<>() {{
                put("numOfRows", Integer.toString(numOfRows));
                put("pageNo", Integer.toString(pageNo));
                put("base_date", base_date);
                put("base_time", base_time);
                put("nx", Integer.toString(nx));
                put("ny", Integer.toString(ny));
            }};
        }
    }

    @NoArgsConstructor
    public static class UltraShortParamBuilder {
        private int numOfRows = 100;
        private int pageNo = 1;
        private String base_date;
        private String base_time;
        private int nx, ny;

        public UltraShortParamBuilder numOfRows(int numOfRows) {
            this.numOfRows = numOfRows;
            return this;
        }

        public UltraShortParamBuilder pageNo(int pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        public UltraShortParamBuilder base_date(String base_date) {
            this.base_date = base_date;
            return this;
        }

        public UltraShortParamBuilder base_time(String base_time) {
            this.base_time = base_time;
            return this;
        }

        public UltraShortParamBuilder nx(int nx) {
            this.nx = nx;
            return this;
        }

        public UltraShortParamBuilder ny(int ny) {
            this.ny = ny;
            return this;
        }

        public Map<String, String> build() {
            return new HashMap<>() {{
                put("numOfRows", Integer.toString(numOfRows));
                put("pageNo", Integer.toString(pageNo));
                put("base_date", base_date);
                put("base_time", base_time);
                put("nx", Integer.toString(nx));
                put("ny", Integer.toString(ny));
            }};
        }
    }
}
