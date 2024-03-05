package com.mzuha.newsparser.event.listener;

import com.mzuha.newsparser.JavaFXApplication;
import com.mzuha.newsparser.NewsParserApplication;
import com.mzuha.newsparser.event.NewsReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class NewsReadyEventListener implements ApplicationListener<NewsReadyEvent> {
    @Override
    public void onApplicationEvent(NewsReadyEvent event) {
        JavaFXApplication.launchJavaFXApp(NewsParserApplication.args);

    }
}
