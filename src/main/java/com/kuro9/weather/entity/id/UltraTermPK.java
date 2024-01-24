package com.kuro9.weather.entity.id;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Builder
public class UltraTermPK implements Serializable {
    private String baseDate;
    private String baseTime;
    private String category;
    private int nx;
    private int ny;
}
