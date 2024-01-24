package com.kuro9.weather.entity.id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class ShortTermPK implements Serializable {
    private String fcstDate;
    private String fcstTime;
    private String category;
    private int nx;
    private int ny;
}
