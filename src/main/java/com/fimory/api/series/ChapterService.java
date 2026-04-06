package com.fimory.api.series;

import com.fimory.api.common.NotFoundException;
import com.fimory.api.domain.ChapterEntity;
import com.fimory.api.domain.SeriesEntity;
import com.fimory.api.repository.ChapterRepository;
import com.fimory.api.repository.SeriesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final SeriesRepository seriesRepository;

    public ChapterService(ChapterRepository chapterRepository, SeriesRepository seriesRepository) {
        this.chapterRepository = chapterRepository;
        this.seriesRepository = seriesRepository;
    }

    public List<ChapterDto> getChaptersBySeriesId(Long seriesId) {
        return chapterRepository.findBySeriesIdOrderByChapterNumber(seriesId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ChapterDto getChapterById(Long id) {
        ChapterEntity chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chapter not found"));
        return toDto(chapter);
    }

    @Transactional
    public ChapterDto createChapter(Long seriesId, ChapterDto chapterDto) {
        SeriesEntity series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new NotFoundException("Series not found"));
        ChapterEntity chapter = new ChapterEntity(chapterDto.title(), chapterDto.content(), chapterDto.chapterNumber(), series);
        chapter = chapterRepository.save(chapter);
        return toDto(chapter);
    }

    @Transactional
    public ChapterDto updateChapter(Long id, ChapterDto chapterDto) {
        ChapterEntity chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chapter not found"));
        chapter.setTitle(chapterDto.title());
        chapter.setContent(chapterDto.content());
        chapter.setChapterNumber(chapterDto.chapterNumber());
        chapter.setUpdatedAt(java.time.LocalDateTime.now());
        chapter = chapterRepository.save(chapter);
        return toDto(chapter);
    }

    @Transactional
    public void deleteChapter(Long id) {
        if (!chapterRepository.existsById(id)) {
            throw new NotFoundException("Chapter not found");
        }
        chapterRepository.deleteById(id);
    }

    private ChapterDto toDto(ChapterEntity chapter) {
        return new ChapterDto(chapter.getId(), chapter.getTitle(), chapter.getContent(), chapter.getChapterNumber(), chapter.getCreatedAt(), chapter.getUpdatedAt(), chapter.getSeries().getId());
    }
}