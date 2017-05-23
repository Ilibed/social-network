package com.ilibed.message;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MessageServiceTests {

    @Autowired
    private MessageService messageService;

    @Test
    public void createMessage_CorrectParameters_ShouldReturnMessageObject(){
        //arrange
        String messageData = "{\"id\":\"12\", \"subject\":\"Some text\", \"sendDate\":\"23.05.2017\", \"receiverId\":\"48\"}";
        Integer senderId = 15;

        //act
        Message actual = messageService.createMessage(messageData, senderId);

        //assert
        Message expected = new Message();
        expected.setId(12);
        expected.setReceiverId(48);
        expected.setSendDate("23.05.2017");
        expected.setSubject("Some text");
        expected.setSenderId(senderId);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void createMessage_NullMessageDataParameter_ShouldThrowNullPointerException(){
        //arrange
        String messageData = null;
        Integer senderId = 15;

        //act
        Message actual = messageService.createMessage(messageData, senderId);
    }

    @Test
    public void getSenderIdByEmail_AdminEmail_ShouldReturnAdminId(){
        //arrange
        String underTest = "ilibed.socialnetwork@gmail.com";

        //act
        Integer actual = messageService.getSenderIdByEmail(underTest);

        //assert
        Integer expected = 1;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getSenderIdByEmail_NotExistingEmail_ShouldReturnNull(){
        //arrange
        String underTest = "not_existing_email@gmail.com";

        //act
        Integer actual = messageService.getSenderIdByEmail(underTest);

        //assert
        Assert.assertNull(actual);
    }

    @Test(expected = NullPointerException.class)
    public void getSenderIdByEmail_NotEmail_ShouldThrowNullPointerException(){
        //arrange
        String underTest = null;

        //act
        Integer actual = messageService.getSenderIdByEmail(underTest);
    }
}
