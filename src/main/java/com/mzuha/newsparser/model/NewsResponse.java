package com.mzuha.newsparser.model;

import lombok.Data;

import java.util.List;

@Data
public class NewsResponse {
    private int totalResults;
    private List<ArticlesItem> articles;
    private String status;
}