package com.kuro9.weather.apitest;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShortTermTest extends ApiTest {
    @Test
    public void getForecast() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/short-term/forecast?nx={nx}&ny={ny}&hourOffset={hourOffset}", "55", "127", "3"))
                .andExpect(status().isOk())
                .andDo(document("get-short-term-forecast",
                        requestParameters(
                                parameterWithName("nx").description("지역 좌표(기상청 페이지 참조)"),
                                parameterWithName("ny").description("지역 좌표(기상청 페이지 참조)"),
                                parameterWithName("hourOffset").description("현재로부터 볼 예보의 시간 차이")
                        )));
    }
}
