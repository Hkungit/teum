package com.teum.repository;

import com.teum.model.Content;
import com.teum.model.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    Page<Content> findByPublishedTrue(Pageable pageable);
    Page<Content> findByTypeAndPublishedTrue(ContentType type, Pageable pageable);
    Page<Content> findByAuthorId(Long authorId, Pageable pageable);
}
