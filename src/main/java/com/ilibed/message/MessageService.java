package com.ilibed.message;

import com.ilibed.user.UserService;
import com.ilibed.util.GsonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private UserService userService;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserService userService){
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public Integer getSenderIdByEmail(String email){
        if(email == null){
            throw new NullPointerException("MessageService, getSenderIdByEmail : email parameter is null");
        }

        return userService.getIdByEmail(email);
    }

    public Message createMessage(String messageData, Integer senderId){
        if(messageData == null){
            throw new NullPointerException("MessageService, getSenderIdByEmail : messageData parameter is null");
        }

        Message message = GsonHandler.fromJson(Message.class, messageData);
        message.setSenderId(senderId);

        return message;
    }

    public Message saveMessage(Message message){
        if(message == null){
            throw new NullPointerException("MessageService, saveMessage : message parameter is null");
        }

        return messageRepository.save(message);
    }

    public String getStringMessageDescription(Message message){
        if(message == null){
            throw new NullPointerException("MessageService, getStringMessageDescription : message parameter is null");
        }
        return GsonHandler.toJson(message);
    }

    public List<Message> findAllMessages(Integer userId){
        if(userId == null){
            throw new NullPointerException("MessageService, findAllMessages : userId parameter is null");
        }

        return messageRepository.findAllMessagesForUser(userId);
    }

    public Map<Integer, MessagesDTOObject> findAllMessagesAsMap(Integer userId){
        if(userId == null){
            throw new NullPointerException("MessageService, findAllMessagesAsMap : userId parameter is null");
        }

        List<Message> messages = findAllMessages(userId);
        Map<Integer, MessagesDTOObject> messageMap = new HashMap<>();

        for (Message message : messages) {
            Integer otherUserId = message.getSenderId().equals(userId) ? message.getReceiverId() : message.getSenderId();
            if (!messageMap.containsKey(otherUserId)) {
                messageMap.put(otherUserId, new MessagesDTOObject(new ArrayList<Message>(),
                        userService.getSimpleUserInfo(otherUserId)));
            }

            messageMap.get(otherUserId).addToList(message);
        }

        return messageMap;
    }
}
