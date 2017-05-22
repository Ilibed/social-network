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

    public List<Message> findAllMessages(Integer userId){
        //return (List<Message>) messageRepository.findAllBySenderIdOrReceiverId(userId, userId);
        return messageRepository.findAllMessagesForUser(userId);
    }

    public Map<Integer, MessagesDTOObject> findAllMessagesAsMap(Integer userId){
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
