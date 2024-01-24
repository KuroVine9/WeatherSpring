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
public class UltraTermDto {
    private String baseDate;
    private String baseTime;

    private int nx;
    private int ny;

    private List<UltraTermCategory> items;

    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Getter
    public static class UltraTermCategory {
        private String category;
        private String fcstDate;
        private String fcstTime;

        /* 예보 값 */
        private String fcstValue;
    }
}
