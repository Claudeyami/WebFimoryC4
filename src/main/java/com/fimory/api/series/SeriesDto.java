package com.fimory.api.series;

import java.time.LocalDateTime;

public record SeriesDto(Long id, String title, String description, String coverImage, LocalDateTime createdAt, LocalDateTime updatedAt) {
}