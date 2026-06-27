package com.github.virtuasport2.memoriawebapp.logging;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import com.github.virtuasport2.memoriawebapp.websocket.LogWebSocketHandler;

@Configuration
public class LogBridge {

    private final LogWebSocketHandler handler;

    public LogBridge(LogWebSocketHandler handler) {
        this.handler = handler;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        WebSocketLogAppender.setWebSocketHandler(handler);
    }
}