package com.teum.repository;

import com.teum.model.MarketingCampaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MarketingCampaignRepository extends JpaRepository<MarketingCampaign, Long> {
    @Query("SELECT c FROM MarketingCampaign c WHERE c.startDate <= :now AND c.endDate >= :now")
    Page<MarketingCampaign> findActiveCampaigns(LocalDateTime now, Pageable pageable);
    
    Page<MarketingCampaign> findByEndDateAfter(LocalDateTime date, Pageable pageable);
    
    MarketingCampaign findByCouponCode(String couponCode);
}
