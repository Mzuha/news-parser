package com.mzuha.newsparser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mzuha.newsparser.entity.ArticleEntity;
import com.mzuha.newsparser.event.publisher.NewsReadyEventPublisher;
import com.mzuha.newsparser.model.NewsResponse;
import com.mzuha.newsparser.repository.ArticleRepository;
import com.mzuha.newsparser.util.ArticleMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduledParser {

    private static boolean isExecuted = false;

    private final NewsWebClientService newsWebClientService;

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    private final ObjectMapper objectMapper;

    private final NewsReadyEventPublisher newsReadyEventPublisher;

    public ScheduledParser(NewsWebClientService newsWebClientService, ArticleRepository articleRepository, ArticleMapper articleMapper, ObjectMapper objectMapper, NewsReadyEventPublisher newsReadyEventPublisher) {
        this.newsWebClientService = newsWebClientService;
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.objectMapper = objectMapper;
        this.newsReadyEventPublisher = newsReadyEventPublisher;
    }

    @Scheduled(fixedRate = 60000)
    public void parseNews() {
        Optional<String> optionalResponse = newsWebClientService.performGetRequest();
        if (optionalResponse.isPresent()) {
            NewsResponse response = parseResponse(optionalResponse.get());

            response.getArticles().forEach(
                (articlesItem) -> {
                    ArticleEntity articleEntity = articleMapper.mapToEntity(articlesItem);
                    if (articleRepository.findByHeadline(articlesItem.getTitle()).isEmpty()) {
                        articleRepository.save(articleEntity);
                    }
                }
            );
        }
        if (!isExecuted) {
            newsReadyEventPublisher.publishNewsReadyEvent();
        }
        isExecuted = true;
    }

    private NewsResponse parseResponse(String response) {
        try {
            return objectMapper.readValue(response, NewsResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
