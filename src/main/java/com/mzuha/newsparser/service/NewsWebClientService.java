package com.mzuha.newsparser.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Optional;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class NewsWebClientService {
    public static final String COUNTRY = "country";
    public static final String US = "us";
    public static final String FROM = "from";
    public static final String API_KEY = "apiKey";
    public static final String PAGE_SIZE = "pageSize";
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
                    .addParameter(COUNTRY, US)
                    .addParameter(FROM, LocalDate.now().toString())
                    .addParameter(API_KEY, apiKey)
                    .addParameter(PAGE_SIZE, String.valueOf(100))
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
