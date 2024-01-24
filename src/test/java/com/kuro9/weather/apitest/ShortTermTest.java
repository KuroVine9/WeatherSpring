package com.kuro9.weather.apitest;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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
                        ),
                        responseFields(
                                fieldWithPath("baseDate").type(JsonFieldType.STRING).description("설정된 날짜(현재와 가장 가까운 일자로 자동 설정)"),
                                fieldWithPath("baseTime").type(JsonFieldType.STRING).description("설정된 시간(현재와 가장 가까운 시각으로 자동 설정)"),
                                fieldWithPath("nx").type(JsonFieldType.NUMBER).description("입력한 좌표"),
                                fieldWithPath("ny").type(JsonFieldType.NUMBER).description("입력한 좌표"),
                                fieldWithPath("items").type(JsonFieldType.ARRAY).description("데이터 리스트"),
                                fieldWithPath("items[].category").type(JsonFieldType.STRING).description("데이터 카테고리(기상청 페이지 참조)"),
                                fieldWithPath("items[].fcstDate").type(JsonFieldType.STRING).description("예보의 해당 일자"),
                                fieldWithPath("items[].fcstTime").type(JsonFieldType.STRING).description("예보의 해당 시각"),
                                fieldWithPath("items[].fcstValue").type(JsonFieldType.STRING).description("예보 값")

                        )
                ));
    }
}
