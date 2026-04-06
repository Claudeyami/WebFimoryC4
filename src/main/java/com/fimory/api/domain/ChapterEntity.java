package com.fimory.api.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Chapters")
public class ChapterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ChapterID")
    private Long id;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Content")
    private String content;

    @Column(name = "ChapterNumber")
    private Integer chapterNumber;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SeriesID")
    private SeriesEntity series;

    // Constructors, getters, setters
    public ChapterEntity() {}

    public ChapterEntity(String title, String content, Integer chapterNumber, SeriesEntity series) {
        this.title = title;
        this.content = content;
        this.chapterNumber = chapterNumber;
        this.series = series;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(Integer chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public SeriesEntity getSeries() {
        return series;
    }

    public void setSeries(SeriesEntity series) {
        this.series = series;
    }
}