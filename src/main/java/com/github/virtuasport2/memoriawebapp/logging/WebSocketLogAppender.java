package com.github.virtuasport2.memoriawebapp.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import org.springframework.stereotype.Component;
import com.github.virtuasport2.memoriawebapp.websocket.LogWebSocketHandler;
import com.github.virtuasport2.memoriawebapp.websocket.WebSocketHandlerHolder;
import com.github.virtuasport2.memoriawebapp.websocket.WebSocketRegistry;

@Component
public class WebSocketLogAppender extends AppenderBase<ILoggingEvent> {

    private LogWebSocketHandler handler;

    public void setWebSocketHandler(LogWebSocketHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void append(ILoggingEvent event) {
        LogWebSocketHandler h = WebSocketHandlerHolder.get();
        if (h != null) {
            h.send(event.getFormattedMessage());
        }
    }
}
