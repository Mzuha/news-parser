package com.mzuha.newsparser.util;

import com.mzuha.newsparser.entity.ArticleEntity;
import com.mzuha.newsparser.model.ArticlesItem;
import org.springframework.stereotype.Service;

@Service
public class ArticleMapper {
    public ArticleEntity mapToEntity(ArticlesItem articlesItem) {
        var entity = new ArticleEntity();
        entity.setHeadline(articlesItem.getTitle());
        entity.setDescription(articlesItem.getDescription());
        entity.setPublicationTime(articlesItem.getPublishedAt());
        return entity;
    }
}
