package com.github.virtuasport2.memoriawebapp.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.github.virtuasport2.memoriawebapp.logging.WebSocketLogAppender;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Bean
    public LogWebSocketHandler logWebSocketHandler() {
        LogWebSocketHandler handler = new LogWebSocketHandler();
        WebSocketLogAppender.setWebSocketHandler(handler);
        return new LogWebSocketHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(logWebSocketHandler(), "/logs")
                .setAllowedOrigins("*");
    }
}