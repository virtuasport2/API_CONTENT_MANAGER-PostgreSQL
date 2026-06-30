package com.github.virtuasport2.memoriawebapp.websocket;

public class WebSocketHandlerHolder {
    private static LogWebSocketHandler handler;

    public static void set(LogWebSocketHandler h) {
        handler = h;
    }

    public static LogWebSocketHandler get() {
        return handler;
    }
}