package com.mzuha.newsparser.model;

import java.util.List;
import lombok.Data;

@Data
public class NewsResponse {
    private int totalResults;
    private List<ArticlesItem> articles;
    private String status;
}