package com.mzuha.newsparser.model;

import lombok.Data;

@Data
public class ArticlesItem {
    private String publishedAt;
    private String author;
    private String urlToImage;
    private String description;
    private Source source;
    private String title;
    private String url;
    private String content;
}
