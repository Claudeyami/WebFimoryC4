package com.fimory.api.series;

import java.time.LocalDateTime;

public record ChapterDto(Long id, String title, String content, Integer chapterNumber, LocalDateTime createdAt, LocalDateTime updatedAt, Long seriesId) {
}