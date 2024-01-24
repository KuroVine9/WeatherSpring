package com.kuro9.weather.apitest;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UltraTermTest extends ApiTest {
    @Test
    public void getForecast() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/ultra-short-term/forecast?nx={nx}&ny={ny}", "55", "127"))
                .andExpect(status().isOk())
                .andDo(document("get-ultra-short-term-forecast",
                        requestParameters(
                                parameterWithName("nx").description("지역 좌표(기상청 페이지 참조)"),
                                parameterWithName("ny").description("지역 좌표(기상청 페이지 참조)")
                        )));
    }
}
