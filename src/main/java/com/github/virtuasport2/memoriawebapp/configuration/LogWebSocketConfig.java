package com.github.virtuasport2.memoriawebapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.virtuasport2.memoriawebapp.logging.LogBridge;
import com.github.virtuasport2.memoriawebapp.logging.WebSocketLogAppender;
import com.github.virtuasport2.memoriawebapp.websocket.LogWebSocketHandler;

import jakarta.annotation.PostConstruct;

@Configuration
public class LogWebSocketConfig {
    @Bean
    public LogBridge logBridge(LogWebSocketHandler handler,
            WebSocketLogAppender appender) {
        return new LogBridge(handler, appender);
    }

    @PostConstruct
    public void test() {
        System.out.println("LOGBRIDGE CREATED");
    }
}