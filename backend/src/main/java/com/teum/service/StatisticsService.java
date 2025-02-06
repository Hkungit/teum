package com.teum.service;

import com.teum.model.*;
import com.teum.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatisticsService {
    @Autowired
    private StatisticsRepository statisticsRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private MarketingCampaignRepository campaignRepository;

    @Scheduled(cron = "0 0 * * * *") // Run every hour
    @Transactional
    public void generateHourlyStatistics() {
        Statistics stats = new Statistics();
        LocalDateTime now = LocalDateTime.now();

        // Order statistics
        long totalOrders = orderRepository.count();
        stats.setTotalOrders((int) totalOrders);

        // Calculate total revenue and average order value
        List<Order> orders = orderRepository.findAll();
        BigDecimal totalRevenue = orders.stream()
            .map(Order::getTotalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.setTotalRevenue(totalRevenue);
        
        if (totalOrders > 0) {
            stats.setAverageOrderValue(totalRevenue.divide(BigDecimal.valueOf(totalOrders), 2, RoundingMode.HALF_UP));
        }

        // User statistics
        stats.setTotalUsers((int) userRepository.count());
        
        // Product statistics
        stats.setTotalProducts((int) productRepository.count());
        stats.setLowStockProducts((int) productRepository.findAll().stream()
            .filter(p -> p.getStockQuantity() < 10)
            .count());

        // Campaign statistics
        stats.setTotalCampaigns((int) campaignRepository.count());
        stats.setActiveCampaigns((int) campaignRepository.findActiveCampaigns(now, null).getTotalElements());

        statisticsRepository.save(stats);
    }

    public Statistics getLatestStatistics() {
        return statisticsRepository.findFirstByOrderByDateDesc();
    }

    public List<Statistics> getStatisticsForPeriod(LocalDateTime start, LocalDateTime end) {
        return statisticsRepository.findByDateBetweenOrderByDateDesc(start, end);
    }

    public Statistics getTodayStatistics() {
        return statisticsRepository.findTodayStatistics();
    }
}
