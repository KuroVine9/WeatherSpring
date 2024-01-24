package com.kuro9.weather;

import com.kuro9.weather.dataclass.apicall.MidApiResponse;
import com.kuro9.weather.service.apicall.KmaApiImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JsonParseTest {
    @Autowired
    KmaApiImpl api;

    @Test
    public void jsonParseTest() {
        String jsonStr = """
                {
                  "response": {
                    "header": { "resultCode": "00", "resultMsg": "NORMAL_SERVICE" },
                    "body": {
                      "dataType": "JSON",
                      "items": {
                        "item": [
                          {
                            "regId": "11B00000",
                            "rnSt3Am": 0,
                            "rnSt3Pm": 0,
                            "rnSt4Am": 10,
                            "rnSt4Pm": 10,
                            "rnSt5Am": 10,
                            "rnSt5Pm": 10,
                            "rnSt6Am": 10,
                            "rnSt6Pm": 10,
                            "rnSt7Am": 10,
                            "rnSt7Pm": 30,
                            "rnSt8": 30,
                            "rnSt9": 40,
                            "rnSt10": 40,
                            "wf3Am": "맑음",
                            "wf3Pm": "맑음",
                            "wf4Am": "맑음",
                            "wf4Pm": "맑음",
                            "wf5Am": "맑음",
                            "wf5Pm": "맑음",
                            "wf6Am": "맑음",
                            "wf6Pm": "맑음",
                            "wf7Am": "맑음",
                            "wf7Pm": "구름많음",
                            "wf8": "구름많음",
                            "wf9": "흐림",
                            "wf10": "흐림"
                          }
                        ]
                      },
                      "pageNo": 1,
                      "numOfRows": 50,
                      "totalCount": 1
                    }
                  }
                }
                """;

        MidApiResponse result = ReflectionTestUtils.invokeMethod(api, "toJson", jsonStr, MidApiResponse.class);
        assertEquals("NORMAL_SERVICE", Objects.requireNonNull(result).response.header.resultMsg);
        assertEquals("11B00000", Objects.requireNonNull(result).response.body.items.item.get(0).getRegId());
    }
}
