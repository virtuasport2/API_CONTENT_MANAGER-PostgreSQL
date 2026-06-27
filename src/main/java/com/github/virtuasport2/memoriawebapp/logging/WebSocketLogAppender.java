package com.github.virtuasport2.memoriawebapp.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.github.virtuasport2.memoriawebapp.websocket.LogWebSocketHandler;

public class WebSocketLogAppender extends AppenderBase<ILoggingEvent> {

    private static LogWebSocketHandler webSocketHandler;

    public static void setWebSocketHandler(LogWebSocketHandler handler) {
        webSocketHandler = handler;
    }

@Override
protected void append(ILoggingEvent event) {

    String message = event.getFormattedMessage();

    System.out.println("WS LOG: " + message);

    if (webSocketHandler != null) {
        webSocketHandler.send(message);
    }
}

    
}