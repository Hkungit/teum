package com.teum.controller;

import com.teum.model.MarketingCampaign;
import com.teum.service.MarketingCampaignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/marketing")
@Tag(name = "Marketing", description = "Marketing campaign management APIs")
public class MarketingController {
    @Autowired
    private MarketingCampaignService campaignService;

    @GetMapping("/campaigns")
    @Operation(summary = "Get active campaigns", description = "Retrieves all currently active marketing campaigns")
    public ResponseEntity<Page<MarketingCampaign>> getActiveCampaigns(Pageable pageable) {
        return ResponseEntity.ok(campaignService.getActiveCampaigns(pageable));
    }

    @GetMapping("/campaigns/upcoming")
    @Operation(summary = "Get upcoming campaigns", description = "Retrieves all upcoming marketing campaigns")
    public ResponseEntity<Page<MarketingCampaign>> getUpcomingCampaigns(Pageable pageable) {
        return ResponseEntity.ok(campaignService.getUpcomingCampaigns(pageable));
    }

    @GetMapping("/campaigns/{id}")
    @Operation(summary = "Get campaign details", description = "Retrieves details of a specific marketing campaign")
    public ResponseEntity<MarketingCampaign> getCampaign(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.getCampaignById(id));
    }

    @PostMapping("/campaigns")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create campaign", description = "Creates a new marketing campaign")
    public ResponseEntity<MarketingCampaign> createCampaign(@Valid @RequestBody MarketingCampaign campaign) {
        return ResponseEntity.ok(campaignService.createCampaign(campaign));
    }

    @PutMapping("/campaigns/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update campaign", description = "Updates an existing marketing campaign")
    public ResponseEntity<MarketingCampaign> updateCampaign(
            @PathVariable Long id,
            @Valid @RequestBody MarketingCampaign campaignDetails) {
        return ResponseEntity.ok(campaignService.updateCampaign(id, campaignDetails));
    }

    @DeleteMapping("/campaigns/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete campaign", description = "Deletes a marketing campaign")
    public ResponseEntity<?> deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/campaigns/coupon/{code}")
    @Operation(summary = "Validate coupon", description = "Validates a coupon code and returns campaign details if valid")
    public ResponseEntity<MarketingCampaign> validateCoupon(@PathVariable String code) {
        return ResponseEntity.ok(campaignService.getCampaignByCouponCode(code));
    }
}
