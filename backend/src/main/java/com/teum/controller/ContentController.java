package com.teum.controller;

import com.teum.model.Content;
import com.teum.model.ContentType;
import com.teum.service.ContentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/content")
public class ContentController {
    @Autowired
    private ContentService contentService;

    @GetMapping
    public ResponseEntity<Page<Content>> getPublishedContent(Pageable pageable) {
        return ResponseEntity.ok(contentService.getPublishedContent(pageable));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<Page<Content>> getContentByType(
            @PathVariable ContentType type,
            Pageable pageable) {
        return ResponseEntity.ok(contentService.getContentByType(type, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> getContent(@PathVariable Long id) {
        return ResponseEntity.ok(contentService.getContentById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Content> createContent(@Valid @RequestBody Content content) {
        return ResponseEntity.ok(contentService.createContent(content));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Content> updateContent(
            @PathVariable Long id,
            @Valid @RequestBody Content contentDetails) {
        return ResponseEntity.ok(contentService.updateContent(id, contentDetails));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.ok().build();
    }
}
