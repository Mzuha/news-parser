package com.mzuha.newsparser.event.publisher;

import com.mzuha.newsparser.event.NewsReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class NewsReadyEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public NewsReadyEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishNewsReadyEvent() {
        NewsReadyEvent newsReadyEvent = new NewsReadyEvent(this);
        applicationEventPublisher.publishEvent(newsReadyEvent);
    }
}
