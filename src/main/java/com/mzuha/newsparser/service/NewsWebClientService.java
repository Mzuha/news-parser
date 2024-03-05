package com.mzuha.newsparser.service;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class NewsWebClientService {
    private final WebClient webClient;

    @Value("${mzuha.api.url}")
    private String apiUrl;

    @Value("${mzuha.api.apiKey}")
    private String apiKey;

    public NewsWebClientService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Optional<String> performGetRequest() {
        URI uri;
        try {
            uri = new URIBuilder(apiUrl)
                .addParameter("country", "us")
                .addParameter("from", LocalDate.now().toString())
                .addParameter("apiKey", apiKey)
                .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid url format!");
        }

        return webClient.get()
            .uri(uri)
            .retrieve()
            .bodyToMono(String.class)
            .blockOptional();
    }
}
