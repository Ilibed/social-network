package com.ilibed.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MessageController {
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @RequestMapping(value = "api/get/messages")
    @ResponseBody
    public ResponseEntity<Map<Integer, MessagesDTOObject>> findAllMessages(@RequestParam Integer userId){
        Map<Integer, MessagesDTOObject> messages = messageService.findAllMessagesAsMap(userId);
        if (messages == null){
            return new ResponseEntity<>(messages, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
