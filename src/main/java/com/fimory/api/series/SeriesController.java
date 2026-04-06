package com.fimory.api.series;

import com.fimory.api.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
public class SeriesController {

    private final SeriesService seriesService;
    private final ChapterService chapterService;

    public SeriesController(SeriesService seriesService, ChapterService chapterService) {
        this.seriesService = seriesService;
        this.chapterService = chapterService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SeriesDto>>> getAllSeries() {
        return ResponseEntity.ok(ApiResponse.ok(seriesService.getAllSeries()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SeriesDto>> getSeriesById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(seriesService.getSeriesById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SeriesDto>> createSeries(@RequestBody SeriesDto seriesDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(seriesService.createSeries(seriesDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SeriesDto>> updateSeries(@PathVariable Long id, @RequestBody SeriesDto seriesDto) {
        return ResponseEntity.ok(ApiResponse.ok(seriesService.updateSeries(id, seriesDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSeries(@PathVariable Long id) {
        seriesService.deleteSeries(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    @GetMapping("/{id}/chapters")
    public ResponseEntity<ApiResponse<List<ChapterDto>>> getChaptersBySeriesId(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(chapterService.getChaptersBySeriesId(id)));
    }

    @GetMapping("/chapters/{chapterId}")
    public ResponseEntity<ApiResponse<ChapterDto>> getChapterById(@PathVariable Long chapterId) {
        return ResponseEntity.ok(ApiResponse.ok(chapterService.getChapterById(chapterId)));
    }

    @PostMapping("/{id}/chapters")
    public ResponseEntity<ApiResponse<ChapterDto>> createChapter(@PathVariable Long id, @RequestBody ChapterDto chapterDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(chapterService.createChapter(id, chapterDto)));
    }

    @PutMapping("/chapters/{chapterId}")
    public ResponseEntity<ApiResponse<ChapterDto>> updateChapter(@PathVariable Long chapterId, @RequestBody ChapterDto chapterDto) {
        return ResponseEntity.ok(ApiResponse.ok(chapterService.updateChapter(chapterId, chapterDto)));
    }

    @DeleteMapping("/chapters/{chapterId}")
    public ResponseEntity<ApiResponse<Void>> deleteChapter(@PathVariable Long chapterId) {
        chapterService.deleteChapter(chapterId);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}