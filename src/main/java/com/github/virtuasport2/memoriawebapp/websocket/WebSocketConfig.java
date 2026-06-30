package com.github.virtuasport2.memoriawebapp.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import com.github.virtuasport2.memoriawebapp.logging.WebSocketLogAppender;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
 
    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;
    private final LogWebSocketHandler logWebSocketHandler;

    public WebSocketConfig(LogWebSocketHandler logWebSocketHandler,
                           JwtHandshakeInterceptor jwtHandshakeInterceptor) {
        this.logWebSocketHandler = logWebSocketHandler;
        this.jwtHandshakeInterceptor = jwtHandshakeInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        System.out.println(jwtHandshakeInterceptor instanceof HandshakeInterceptor);

         registry.addHandler(logWebSocketHandler, "/logs")
                .addInterceptors(jwtHandshakeInterceptor)
                .setAllowedOrigins("*");
    }
}