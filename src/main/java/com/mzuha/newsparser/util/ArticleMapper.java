package com.mzuha.newsparser.util;

import com.mzuha.newsparser.entity.ArticleEntity;
import com.mzuha.newsparser.model.ArticlesItem;
import java.time.ZonedDateTime;
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

    public ArticlesItem mapToItem(ArticleEntity articleEntity) {
        var item = new ArticlesItem();
        item.setId(articleEntity.getId());
        item.setTitle(articleEntity.getHeadline());
        item.setDescription(articleEntity.getDescription());
        item.setPublishedAt(articleEntity.getPublicationTime());
        if (!articleEntity.getPublicationTime().isEmpty()) {
            item.setZonedPublishedAt(
                    ZonedDateTime.parse(articleEntity.getPublicationTime())
            );
        }
        return item;
    }
}
