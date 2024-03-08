package com.mzuha.newsparser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mzuha.newsparser.entity.ArticleEntity;
import com.mzuha.newsparser.model.NewsResponse;
import com.mzuha.newsparser.util.ArticleMapper;
import java.util.Collections;
import java.util.Optional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTasks {

    public static final int TWENTY_MINUTES = 1200000;
    private final NewsWebClientService newsWebClientService;

    private final ArticleService articleService;

    private final ArticleMapper articleMapper;

    private final ObjectMapper objectMapper;

    public ScheduledTasks(NewsWebClientService newsWebClientService, ArticleService articleService, ArticleMapper articleMapper, ObjectMapper objectMapper) {
        this.newsWebClientService = newsWebClientService;
        this.articleService = articleService;
        this.articleMapper = articleMapper;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = TWENTY_MINUTES, initialDelay = TWENTY_MINUTES)
    public int parseNews() {
        int newsSize = 0;
        Optional<String> optionalResponse = newsWebClientService.performGetRequest();
        if (optionalResponse.isPresent()) {

            NewsResponse response = parseResponse(optionalResponse.get());

            newsSize = response.getArticles().size();

            response.getArticles().forEach(
                    (articlesItem) -> {
                        ArticleEntity articleEntity = articleMapper.mapToEntity(articlesItem);
                        if (articleService.findByHeadline(articlesItem.getTitle()).isEmpty()) {
                            articleService.save(articleEntity);
                        }
                    }
            );
        }
        return newsSize;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void cleanOldDataDaily() {
        articleService.clearOldNews();
    }

    private NewsResponse parseResponse(String response) {
        try {
            return objectMapper.readValue(response, NewsResponse.class);
        } catch (JsonProcessingException e) {
            NewsResponse emptyResponse = new NewsResponse();
            emptyResponse.setArticles(Collections.emptyList());
            return emptyResponse;
        }
    }
}
