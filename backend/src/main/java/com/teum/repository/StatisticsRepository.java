package com.teum.repository;

import com.teum.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    Statistics findFirstByOrderByDateDesc();
    
    List<Statistics> findByDateBetweenOrderByDateDesc(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT s FROM Statistics s WHERE DATE(s.date) = CURRENT_DATE")
    Statistics findTodayStatistics();
}
