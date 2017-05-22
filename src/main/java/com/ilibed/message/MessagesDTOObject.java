package com.ilibed.message;

import com.ilibed.user.SimpleUser;

import java.util.List;

public class MessagesDTOObject {
    private SimpleUser simpleUser;
    private List<Message> messageList;

    public MessagesDTOObject(List<Message> messageList, SimpleUser simpleUser){
        this.messageList = messageList;
        this.simpleUser = simpleUser;
    }

    public SimpleUser getSimpleUser() {
        return simpleUser;
    }

    public void setSimpleUser(SimpleUser simpleUser) {
        this.simpleUser = simpleUser;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public void addToList(Message message){
        messageList.add(message);
    }
}
