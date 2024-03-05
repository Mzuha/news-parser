package com.mzuha.newsparser.repository;

import com.mzuha.newsparser.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    Optional<ArticleEntity> findByHeadline(String headline);
}
