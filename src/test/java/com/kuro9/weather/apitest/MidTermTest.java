package com.kuro9.weather.apitest;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MidTermTest extends ApiTest {

    @Test
    public void getForecast() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/mid-term/forecast?regId={regId}", "11B00000"))
                .andExpect(status().isOk())
                .andDo(document("get-mid-term-forecast",
                        requestParameters(
                                parameterWithName("regId").description("지역 코드(기상청 페이지 참조)")
                        )));
    }
}
