package com.teum.service;

import com.teum.model.Content;
import com.teum.model.ContentType;
import com.teum.repository.ContentRepository;
import com.teum.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContentService {
    @Autowired
    private ContentRepository contentRepository;

    @Transactional
    public Content createContent(Content content) {
        return contentRepository.save(content);
    }

    public Content getContentById(Long id) {
        return contentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + id));
    }

    public Page<Content> getPublishedContent(Pageable pageable) {
        return contentRepository.findByPublishedTrue(pageable);
    }

    public Page<Content> getContentByType(ContentType type, Pageable pageable) {
        return contentRepository.findByTypeAndPublishedTrue(type, pageable);
    }

    public Page<Content> getContentByAuthor(Long authorId, Pageable pageable) {
        return contentRepository.findByAuthorId(authorId, pageable);
    }

    @Transactional
    public Content updateContent(Long id, Content contentDetails) {
        Content content = getContentById(id);
        content.setTitle(contentDetails.getTitle());
        content.setContent(contentDetails.getContent());
        content.setImageUrl(contentDetails.getImageUrl());
        content.setPublished(contentDetails.isPublished());
        content.setPublishDate(contentDetails.getPublishDate());
        return contentRepository.save(content);
    }

    @Transactional
    public void deleteContent(Long id) {
        Content content = getContentById(id);
        contentRepository.delete(content);
    }
}
