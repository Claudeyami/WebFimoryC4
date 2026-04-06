package com.fimory.api.repository;

import com.fimory.api.domain.SeriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends JpaRepository<SeriesEntity, Long> {
}