package com.ilibed.message;

import com.ilibed.user.UserService;
import com.ilibed.util.GsonHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageService {
    private MessageRepository messageRepository;
    private UserService userService;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserService userService){
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public Integer getSenderIdByEmail(String email){
        return userService.getIdByEmail(email);
    }

    public Message createMessage(String messageData, Integer senderId){
        Message message = GsonHandler.fromJson(Message.class, messageData);
        message.setSenderId(senderId);

        return message;
    }

    public Message saveMessage(Message message){
        return messageRepository.save(message);
    }

    public String getStringMessageDescription(Message message){
        return GsonHandler.toJson(message);
    }
}
