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
public class MidTermPK implements Serializable {
    // 지역 코드
    private String regId;

    // 발표 시간(0600 or 1800시)
    private String tmFc;
}
