package com.kuro9.weather.repository;

import com.kuro9.weather.entity.UltraTerm;
import com.kuro9.weather.entity.id.UltraTermPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UltraTermRepository extends JpaRepository<UltraTerm, UltraTermPK> {
    List<UltraTerm> findAllByIdBaseDateAndIdBaseTimeAndIdNxAndIdNy(String baseDate, String baseTime, int nx, int ny);
}
