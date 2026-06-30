package com.github.virtuasport2.memoriawebapp.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Iterator;

@Component
public class LogWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        String user = (String) session.getAttributes().get("user");

        System.out.println("OPEN USER = " + user);

        
        System.out.println(session.getAttributes());
        System.out.println(session.getAttributes().get("token"));

        // AGGIUNGI QUESTA RIGA
        WebSocketRegistry.handler = this;

        sessions.add(session);
        System.out.println("OPEN: " + session.getId());
        System.out.println("SESSION COUNT: " + sessions.size());
        System.out.println("OPEN HANDLER INSTANCE = " + System.identityHashCode(this));
        System.out.println("INSTANCE: " + System.identityHashCode(this));
        /*
         * NON inviare suibito qui altrimenti, la sessione muore
         * session.sendMessage(new
         * TextMessage("Prova messaggio LogWebSocketHandler : afterConnectionEstablished"
         * ));
         * session.sendMessage(new TextMessage(sessions.toString()));
         */

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {

        System.out.println(session.getAttributes());
        System.out.println(session.getAttributes().get("token"));
        System.out.println("CLOSE: " + status.getCode());
        System.out.println("CLOSE: " + status);
        sessions.remove(session);
    }

    public void send(String message) {
        System.out.println("SEND -> sessions: " + sessions.size());
        System.out.println("INSTANCE: " + System.identityHashCode(this));
        System.out.println("SEND CALLED - sessions: " + sessions.size());

        Iterator<WebSocketSession> it = sessions.iterator();

        while (it.hasNext()) {
            WebSocketSession session = it.next();

            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                    System.out.println("MESSAGE SENT");
                } else {
                    it.remove();
                }

            } catch (IOException e) {
                it.remove();
                System.out.println("SESSION REMOVED");
            }
        }
    }
}