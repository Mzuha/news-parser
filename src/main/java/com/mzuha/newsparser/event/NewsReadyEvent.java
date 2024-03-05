package com.mzuha.newsparser.event;

import org.springframework.context.ApplicationEvent;

public class NewsReadyEvent extends ApplicationEvent {
    public NewsReadyEvent(Object source) {
        super(source);
    }
}
