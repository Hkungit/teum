package com.teum.service;

import com.teum.model.MarketingCampaign;
import org.springframework.cache.annotation.Cacheable;
import com.teum.repository.MarketingCampaignRepository;
import com.teum.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MarketingCampaignService {
    @Autowired
    private MarketingCampaignRepository campaignRepository;

    @Transactional
    public MarketingCampaign createCampaign(MarketingCampaign campaign) {
        return campaignRepository.save(campaign);
    }

    public MarketingCampaign getCampaignById(Long id) {
        return campaignRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + id));
    }

    @Cacheable(value = "activePromotions")
    public Page<MarketingCampaign> getActiveCampaigns(Pageable pageable) {
        return campaignRepository.findActiveCampaigns(LocalDateTime.now(), pageable);
    }

    public Page<MarketingCampaign> getUpcomingCampaigns(Pageable pageable) {
        return campaignRepository.findByEndDateAfter(LocalDateTime.now(), pageable);
    }

    public MarketingCampaign getCampaignByCouponCode(String couponCode) {
        return campaignRepository.findByCouponCode(couponCode);
    }

    @Transactional
    public MarketingCampaign updateCampaign(Long id, MarketingCampaign campaignDetails) {
        MarketingCampaign campaign = getCampaignById(id);
        campaign.setName(campaignDetails.getName());
        campaign.setDescription(campaignDetails.getDescription());
        campaign.setStartDate(campaignDetails.getStartDate());
        campaign.setEndDate(campaignDetails.getEndDate());
        campaign.setType(campaignDetails.getType());
        campaign.setDiscountAmount(campaignDetails.getDiscountAmount());
        campaign.setDiscountPercentage(campaignDetails.getDiscountPercentage());
        campaign.setCouponCode(campaignDetails.getCouponCode());
        campaign.setUsageLimit(campaignDetails.getUsageLimit());
        campaign.setProducts(campaignDetails.getProducts());
        return campaignRepository.save(campaign);
    }

    @Transactional
    public void deleteCampaign(Long id) {
        MarketingCampaign campaign = getCampaignById(id);
        campaignRepository.delete(campaign);
    }

    @Transactional
    public boolean incrementCampaignUsage(Long id) {
        MarketingCampaign campaign = getCampaignById(id);
        if (campaign.getUsageLimit() != null && campaign.getCurrentUsage() >= campaign.getUsageLimit()) {
            return false;
        }
        campaign.setCurrentUsage(campaign.getCurrentUsage() + 1);
        campaignRepository.save(campaign);
        return true;
    }
}
