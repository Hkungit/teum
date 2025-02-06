package com.teum.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "statistics")
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private Integer totalOrders;
    private BigDecimal totalRevenue;
    private Integer totalUsers;
    private Integer activeUsers;
    private Integer totalProducts;
    private Integer lowStockProducts;
    private BigDecimal averageOrderValue;
    private Integer totalCampaigns;
    private Integer activeCampaigns;

    @PrePersist
    protected void onCreate() {
        date = LocalDateTime.now();
    }
}
