package com.teum.controller;

import com.teum.model.Statistics;
import com.teum.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Statistics", description = "Data statistics and analytics APIs")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/latest")
    @Operation(summary = "Get latest statistics", description = "Retrieves the most recent statistics data")
    public ResponseEntity<Statistics> getLatestStatistics() {
        return ResponseEntity.ok(statisticsService.getLatestStatistics());
    }

    @GetMapping("/today")
    @Operation(summary = "Get today's statistics", description = "Retrieves statistics for the current day")
    public ResponseEntity<Statistics> getTodayStatistics() {
        return ResponseEntity.ok(statisticsService.getTodayStatistics());
    }

    @GetMapping("/period")
    @Operation(summary = "Get period statistics", description = "Retrieves statistics for a specific time period")
    public ResponseEntity<List<Statistics>> getStatisticsForPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(statisticsService.getStatisticsForPeriod(start, end));
    }
}
