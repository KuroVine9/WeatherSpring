package com.kuro9.weather.entity;

import com.kuro9.weather.dataclass.data.MidTermDtoData;
import com.kuro9.weather.dataclass.data.MidTermDtoDataInterface;
import com.kuro9.weather.entity.id.MidTermPK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MidTerm implements MidTermDtoDataInterface {

    @EmbeddedId
    private MidTermPK midTermPK;

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

    public MidTerm(String regId, String tmFc, MidTermDtoData data) {
        this(
                new MidTermPK(regId, tmFc),
                data.rnSt3Am, data.rnSt3Pm,
                data.rnSt4Am, data.rnSt4Pm,
                data.rnSt5Am, data.rnSt5Pm,
                data.rnSt6Am, data.rnSt6Pm,
                data.rnSt7Am, data.rnSt7Pm,
                data.rnSt8, data.rnSt9, data.rnSt10,
                data.wf3Am, data.wf3Pm,
                data.wf4Am, data.wf4Pm,
                data.wf5Am, data.wf5Pm,
                data.wf6Am, data.wf6Pm,
                data.wf7Am, data.wf7Pm,
                data.wf8, data.wf9, data.wf10
        );
    }

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
