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


public class MidTermTest extends ApiTest {

    @Test
    public void getForecast() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/mid-term/forecast?regId={regId}", "11B00000"))
                .andExpect(status().isOk())
                .andDo(document("get-mid-term-forecast",
                        requestParameters(
                                parameterWithName("regId").description("지역 코드(기상청 페이지 참조)")
                        ),
                        responseFields(
                                fieldWithPath("regId").type(JsonFieldType.STRING).description("입력한 지역 코드"),
                                fieldWithPath("tmFc").type(JsonFieldType.STRING).description("설정된 시간 코드(현재와 가장 가까운 코드로 자동 설정)"),
                                fieldWithPath("rnSt3Am").type(JsonFieldType.NUMBER).description("3일 후 오전 강수 확률"),
                                fieldWithPath("rnSt3Pm").type(JsonFieldType.NUMBER).description("3일 후 오후 강수 확률"),
                                fieldWithPath("rnSt4Am").type(JsonFieldType.NUMBER).description("4일 후 오전 강수 확률"),
                                fieldWithPath("rnSt4Pm").type(JsonFieldType.NUMBER).description("4일 후 오후 강수 확률"),
                                fieldWithPath("rnSt5Am").type(JsonFieldType.NUMBER).description("5일 후 오전 강수 확률"),
                                fieldWithPath("rnSt5Pm").type(JsonFieldType.NUMBER).description("5일 후 오후 강수 확률"),
                                fieldWithPath("rnSt6Am").type(JsonFieldType.NUMBER).description("6일 후 오전 강수 확률"),
                                fieldWithPath("rnSt6Pm").type(JsonFieldType.NUMBER).description("6일 후 오후 강수 확률"),
                                fieldWithPath("rnSt7Am").type(JsonFieldType.NUMBER).description("7일 후 오전 강수 확률"),
                                fieldWithPath("rnSt7Pm").type(JsonFieldType.NUMBER).description("7일 후 오후 강수 확률"),
                                fieldWithPath("rnSt8").type(JsonFieldType.NUMBER).description("8일 후 강수 확률"),
                                fieldWithPath("rnSt9").type(JsonFieldType.NUMBER).description("9일 후 강수 확률"),
                                fieldWithPath("rnSt10").type(JsonFieldType.NUMBER).description("10일 후 강수 확률"),

                                fieldWithPath("wf3Am").type(JsonFieldType.STRING).description("3일 후 오전 날씨 예보"),
                                fieldWithPath("wf3Pm").type(JsonFieldType.STRING).description("3일 후 오후 날씨 예보"),
                                fieldWithPath("wf4Am").type(JsonFieldType.STRING).description("4일 후 오전 날씨 예보"),
                                fieldWithPath("wf4Pm").type(JsonFieldType.STRING).description("4일 후 오후 날씨 예보"),
                                fieldWithPath("wf5Am").type(JsonFieldType.STRING).description("5일 후 오전 날씨 예보"),
                                fieldWithPath("wf5Pm").type(JsonFieldType.STRING).description("5일 후 오후 날씨 예보"),
                                fieldWithPath("wf6Am").type(JsonFieldType.STRING).description("6일 후 오전 날씨 예보"),
                                fieldWithPath("wf6Pm").type(JsonFieldType.STRING).description("6일 후 오후 날씨 예보"),
                                fieldWithPath("wf7Am").type(JsonFieldType.STRING).description("7일 후 오전 날씨 예보"),
                                fieldWithPath("wf7Pm").type(JsonFieldType.STRING).description("7일 후 오후 날씨 예보"),
                                fieldWithPath("wf8").type(JsonFieldType.STRING).description("8일 후 날씨 예보"),
                                fieldWithPath("wf9").type(JsonFieldType.STRING).description("9일 후 날씨 예보"),
                                fieldWithPath("wf10").type(JsonFieldType.STRING).description("10일 후 날씨 예보")
                        )
                ));
    }
}
