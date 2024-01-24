package com.kuro9.weather.entity;

import com.kuro9.weather.entity.id.ShortTermPK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ShortTerm {

    @EmbeddedId
    private ShortTermPK shortTermPK;

    private String baseDate;
    private String baseTime;

    /* 예보 값 */
    private String fcstValue;
}
