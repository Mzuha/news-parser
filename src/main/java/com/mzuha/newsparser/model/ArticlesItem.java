package com.mzuha.newsparser.model;

import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class ArticlesItem {
    private Long id;
    private String publishedAt;
    private ZonedDateTime zonedPublishedAt;
    private String author;
    private String urlToImage;
    private String description;
    private Source source;
    private String title;
    private String url;
    private String content;
}
