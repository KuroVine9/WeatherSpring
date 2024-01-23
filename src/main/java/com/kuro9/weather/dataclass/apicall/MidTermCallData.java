package com.kuro9.weather.dataclass.apicall;

import com.kuro9.weather.dataclass.data.MidTermDtoData;
import com.kuro9.weather.dataclass.data.MidTermDtoDataInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MidTermCallData implements MidTermDtoDataInterface {
    // 지역 코드
    private String regId;

    /* 강수 확률 */
    private int rnSt3Am;
    private int rnSt3Pm;
    private int rnSt4Am;
    private int rnSt4Pm;
    private int rnSt5Am;
    private int rnSt5Pm;
    private int rnSt6Am;
    private int rnSt6Pm;
    private int rnSt7Am;
    private int rnSt7Pm;
    private int rnSt8;
    private int rnSt9;
    private int rnSt10;

    /* 날씨 예보 */
    private String wf3Am;
    private String wf3Pm;
    private String wf4Am;
    private String wf4Pm;
    private String wf5Am;
    private String wf5Pm;
    private String wf6Am;
    private String wf6Pm;
    private String wf7Am;
    private String wf7Pm;
    private String wf8;
    private String wf9;
    private String wf10;

    @Override
    public MidTermDtoData toData() {
        return new MidTermDtoData(
                rnSt3Am, rnSt3Pm,
                rnSt4Am, rnSt4Pm,
                rnSt5Am, rnSt5Pm,
                rnSt6Am, rnSt6Pm,
                rnSt7Am, rnSt7Pm,
                rnSt8, rnSt9, rnSt10,
                wf3Am, wf3Pm,
                wf4Am, wf4Pm,
                wf5Am, wf5Pm,
                wf6Am, wf6Pm,
                wf7Am, wf7Pm,
                wf8, wf9, wf10
        );
    }
}
