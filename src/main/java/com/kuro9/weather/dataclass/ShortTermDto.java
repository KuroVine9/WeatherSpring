package com.kuro9.weather.dataclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class ShortTermDto {

    private String baseDate;
    private String baseTime;

    private int nx;
    private int ny;

    private List<ShortTermCategory> items;

    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Getter
    public static class ShortTermCategory {
        private String category;
        private String fcstDate;
        private String fcstTime;

        /* 예보 값 */
        private String fcstValue;
    }
}
