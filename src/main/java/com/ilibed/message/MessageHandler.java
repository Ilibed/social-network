package com.ilibed.message;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageHandler extends TextWebSocketHandler {
    private static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Principal principal = session.getPrincipal();
        if (!sessionMap.containsKey(principal.getName())){
            sessionMap.put(principal.getName(), session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.print(message.getPayload());
        session.sendMessage(message);
        super.handleTextMessage(session, message);
    }
}
