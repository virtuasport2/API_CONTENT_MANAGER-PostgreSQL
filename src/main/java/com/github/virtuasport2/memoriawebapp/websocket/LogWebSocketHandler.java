package com.github.virtuasport2.memoriawebapp.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class LogWebSocketHandler extends TextWebSocketHandler {

    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
    }

    public void sendLog(String log) {
        try {
            if (session != null && session.isOpen()) {
                session.sendMessage(new TextMessage(log));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}