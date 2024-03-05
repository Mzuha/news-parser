package com.mzuha.newsparser.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String headline;

    @Column(length = 3000)
    private String description;

    private String publicationTime;
}
