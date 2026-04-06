package com.fimory.api.series;

import com.fimory.api.common.NotFoundException;
import com.fimory.api.domain.SeriesEntity;
import com.fimory.api.repository.SeriesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public List<SeriesDto> getAllSeries() {
        return seriesRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public SeriesDto getSeriesById(Long id) {
        SeriesEntity series = seriesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Series not found"));
        return toDto(series);
    }

    @Transactional
    public SeriesDto createSeries(SeriesDto seriesDto) {
        SeriesEntity series = new SeriesEntity(seriesDto.title(), seriesDto.description(), seriesDto.coverImage());
        series = seriesRepository.save(series);
        return toDto(series);
    }

    @Transactional
    public SeriesDto updateSeries(Long id, SeriesDto seriesDto) {
        SeriesEntity series = seriesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Series not found"));
        series.setTitle(seriesDto.title());
        series.setDescription(seriesDto.description());
        series.setCoverImage(seriesDto.coverImage());
        series.setUpdatedAt(java.time.LocalDateTime.now());
        series = seriesRepository.save(series);
        return toDto(series);
    }

    @Transactional
    public void deleteSeries(Long id) {
        if (!seriesRepository.existsById(id)) {
            throw new NotFoundException("Series not found");
        }
        seriesRepository.deleteById(id);
    }

    private SeriesDto toDto(SeriesEntity series) {
        return new SeriesDto(series.getId(), series.getTitle(), series.getDescription(), series.getCoverImage(), series.getCreatedAt(), series.getUpdatedAt());
    }
}