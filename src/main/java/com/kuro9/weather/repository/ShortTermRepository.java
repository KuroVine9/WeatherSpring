package com.kuro9.weather.repository;

import com.kuro9.weather.entity.ShortTerm;
import com.kuro9.weather.entity.id.ShortTermPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortTermRepository extends JpaRepository<ShortTerm, ShortTermPK> {
    List<ShortTerm> findAllByIdBaseDateAndIdBaseTimeAndIdNxAndIdNy(String date, String time, int nx, int ny);
}
