package com.kuro9.weather.repository;

import com.kuro9.weather.entity.MidTerm;
import com.kuro9.weather.entity.id.MidTermPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MidTermRepository extends JpaRepository<MidTerm, MidTermPK> {
}
