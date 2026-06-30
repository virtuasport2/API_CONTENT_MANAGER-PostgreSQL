package com.github.virtuasport2.memoriawebapp.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.github.virtuasport2.memoriawebapp.service.JwtService;

import java.net.URI;
import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtService jwtService;

    public JwtHandshakeInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    /*
     * @Override
     * public boolean beforeHandshake(
     * ServerHttpRequest request,
     * ServerHttpResponse response,
     * WebSocketHandler wsHandler,
     * Map<String, Object> attributes) {
     * 
     * String query = request.getURI().getQuery();
     * 
     * System.out.println("HANDSHAKE INTERCEPTOR");
     * System.out.println(request.getURI());
     * 
     * if (query != null && query.contains("token=")) {
     * String token = query.split("token=")[1].split("&")[0];
     * System.out.println("TOKEN = " + token);
     * attributes.put("token", token);
     * System.out.println(attributes);
     * 
     * }
     * 
     * 
     * return true;
     * }
     */

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {

        System.out.println("HANDSHAKE INTERCEPTOR");

        URI uri = request.getURI();
        System.out.println(uri);

        String query = uri.getQuery(); // token=xxx
        String token = null;

        if (query != null && query.startsWith("token=")) {
            token = query.substring(6);
        }

        System.out.println("TOKEN = " + token);

        // QUI: valida JWT (già hai classe per farlo)
        String username = null;

        try {
            if (token != null) {
                username = jwtService.extractUsername(token);
            }
        } catch (Exception e) {
            System.out.println("JWT non valido nel WebSocket handshake");
            return false;
        }
        // SALVATAGGIO nella sessione WebSocket
        attributes.put("user", username);

        System.out.println("USER IN SESSION = " + username);

        return true;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception) {
    }

}