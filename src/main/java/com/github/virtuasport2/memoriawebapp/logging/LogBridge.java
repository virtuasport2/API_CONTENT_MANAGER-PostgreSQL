package com.github.virtuasport2.memoriawebapp.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import com.github.virtuasport2.memoriawebapp.websocket.LogWebSocketHandler;
import com.github.virtuasport2.memoriawebapp.websocket.WebSocketHandlerHolder;

public class LogBridge {

    private final LogWebSocketHandler handler;
    private final WebSocketLogAppender appender;

    @Bean
    public LogBridge logBridge(LogWebSocketHandler handler,
            WebSocketLogAppender appender) {
        return new LogBridge(handler, appender);
    }

    public LogBridge(LogWebSocketHandler handler,
            WebSocketLogAppender appender) {
        this.handler = handler;
        this.appender = appender;

        WebSocketHandlerHolder.set(handler);
        System.out.println("LOG BRIDGE CREATED");
        this.appender.setWebSocketHandler(handler);
    }
    /*
     * @EventListener(ApplicationReadyEvent.class)
     * public void init() {
     * System.out.println("LOG BRIDGE INIT");
     * 
     * appender.setWebSocketHandler(handler);
     * }
     */
}