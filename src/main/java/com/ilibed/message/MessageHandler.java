package com.ilibed.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageHandler extends TextWebSocketHandler {
    private static Map<Integer, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    private MessageService messageService;

    @Autowired
    public MessageHandler(MessageService messageService){
        this.messageService = messageService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Principal principal = session.getPrincipal();
        Integer senderId = messageService.getSenderIdByEmail(principal.getName());
        if (!sessionMap.containsKey(senderId)){
            sessionMap.put(senderId, session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Message sendingMessage = createMessage(session, message);
        TextMessage outMessage = saveMessage(sendingMessage);
        session.sendMessage(outMessage);
        sendToReceiver(outMessage, sendingMessage.getReceiverId());
        //super.handleTextMessage(session, message);
    }

    private Message createMessage(WebSocketSession session, TextMessage message){
        Principal principal = session.getPrincipal();
        Integer senderId = messageService.getSenderIdByEmail(principal.getName());
        return messageService.createMessage(message.getPayload(), senderId);
    }

    private TextMessage saveMessage(Message message){
        message = messageService.saveMessage(message);
        TextMessage outMessage = new TextMessage(messageService.getStringMessageDescription(message));
        return outMessage;
    }

    private void sendToReceiver(TextMessage message, Integer receiverId) throws Exception{
        if (sessionMap.containsKey(receiverId)){
            WebSocketSession session = sessionMap.get(receiverId);
            session.sendMessage(message);
        }
    }
}
