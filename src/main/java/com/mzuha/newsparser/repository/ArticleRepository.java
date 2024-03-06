package com.mzuha.newsparser.repository;

import com.mzuha.newsparser.entity.ArticleEntity;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    String CUTOFF_TIME = "cutoffTime";

    Optional<ArticleEntity> findByHeadline(String headline);

    @Transactional
    @Modifying
    @Query("DELETE FROM ArticleEntity a WHERE a.createdAt < :cutoffTime")
    void deleteOldData(@Param(CUTOFF_TIME) Date cutoffTime);
}
