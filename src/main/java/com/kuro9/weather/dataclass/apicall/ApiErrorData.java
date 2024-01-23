package com.kuro9.weather.dataclass.apicall;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JacksonXmlRootElement(localName = "OpenAPI_ServiceResponse")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiErrorData {
    private CmmMsgHeader cmmMsgHeader;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CmmMsgHeader {
        @JacksonXmlProperty(localName = "returnReasonCode")
        private int returnReasonCode;
    }
}