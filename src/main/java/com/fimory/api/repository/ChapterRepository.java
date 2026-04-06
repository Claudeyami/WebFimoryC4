package com.fimory.api.repository;

import com.fimory.api.domain.ChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<ChapterEntity, Long> {
    List<ChapterEntity> findBySeriesIdOrderByChapterNumber(Long seriesId);
}