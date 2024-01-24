package com.kuro9.weather.dataclass.apicall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ShortApiResponse {
    public Response response;

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
                public List<ShortTermCallData> item;
            }
        }
    }
}