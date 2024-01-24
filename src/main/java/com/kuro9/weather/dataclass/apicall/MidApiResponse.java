package com.kuro9.weather.dataclass.apicall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// 제너릭으로 하면 에러
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MidApiResponse implements HeaderInterface {
    public Response response;

    @Override
    public int getResultCode() {
        return Integer.parseInt(response.header.resultCode);
    }

    @Override
    public String getResultMsg() {
        return response.header.resultMsg;
    }

    public static class Response {
        public Header header;
        public Body body;

        public static class Header {
            public String resultCode;
            public String resultMsg;
        }

        public static class Body {
            public String dataType;
            public Items items;
            public int pageNo;
            public int numOfRows;
            public int totalCount;

            public static class Items {
                public List<MidTermCallData> item;
            }
        }
    }
}


