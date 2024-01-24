package com.kuro9.weather.entity;

import com.kuro9.weather.entity.id.UltraTermPK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UltraTerm {
    @EmbeddedId
    private UltraTermPK id;

    private String fcstDate;
    private String fcstTime;

    /* 예보 값 */
    private String fcstValue;
}
