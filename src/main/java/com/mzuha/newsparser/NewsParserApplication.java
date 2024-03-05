package com.mzuha.newsparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsParserApplication {
    public static String[] args;

    public static void main(String[] args) {
        SpringApplication.run(NewsParserApplication.class, args);
        NewsParserApplication.args = args;
    }
}
