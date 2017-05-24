package com.ilibed.message;

import com.ilibed.user.User;
import com.ilibed.user.UserRepository;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.criteria.CriteriaBuilder;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MessageServiceTests {

    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    private User sender;
    private User receiver;

    @Before
    public void createUsers(){
        sender = new User();
        sender.setFirstName("Sender");
        sender.setLastName("Sender");
        sender.setEmail("sender@gmail.com");
        sender.setPassword("1234567");
        sender.setMainPhotoId(null);
        sender.setRoleId(2);
        sender.setBanned(false);
        sender.setCity("");
        sender.setCountry("");

        receiver = new User();
        receiver.setFirstName("Receiver");
        receiver.setLastName("Receiver");
        receiver.setEmail("receiver@gmail.com");
        receiver.setPassword("1234567");
        receiver.setMainPhotoId(null);
        receiver.setRoleId(2);
        receiver.setBanned(false);
        receiver.setCity("");
        receiver.setCountry("");

        sender = userRepository.save(sender);
        receiver = userRepository.save(receiver);
    }

    @After
    public void deleteUsers(){
        userRepository.delete(sender);
        userRepository.delete(receiver);
    }

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
    public void getSenderIdByEmail_SenderEmail_ShouldReturnAdminId(){
        //arrange
        String underTest = sender.getEmail();

        //act
        Integer actual = messageService.getSenderIdByEmail(underTest);

        //assert
        Integer expected = sender.getId();

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

    @Test
    public void saveMessage_ExistingUsers_ShouldSaveMessageToDatabase(){
        //arrange
        Message underTest = new Message();
        underTest.setId(null);
        underTest.setReceiverId(receiver.getId());
        underTest.setSendDate("23.05.2017");
        underTest.setSubject("Some text");
        underTest.setSenderId(sender.getId());

        //act
        Message actual = messageService.saveMessage(underTest);

        //assert
        Assert.assertTrue(messageRepository.exists(actual.getId()));

        //clean
        messageRepository.delete(actual);
    }

    @Test(expected = NullPointerException.class)
    public void saveMessage_NullMessage_ShouldThrowNullPointerException(){
        //arrange
        Message underTest = null;

        //act
        Message actual = messageService.saveMessage(underTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveMessage_NotExistingUsers_ShouldThrowDataIntegrityViolationException(){
        //arrange
        Message underTest = new Message();
        underTest.setId(null);
        underTest.setReceiverId(12456);
        underTest.setSendDate("23.05.2017");
        underTest.setSubject("Some text");
        underTest.setSenderId(25896);

        //act
        Message actual = messageService.saveMessage(underTest);
    }

    @Test
    public void getStringMessageDescription_FullFilledMessage_ShouldReturnStringDescriptionWithAllFields(){
        //arrange
        Message underTest = new Message();
        underTest.setId(12);
        underTest.setReceiverId(48);
        underTest.setSendDate("23.05.2017");
        underTest.setSubject("Some text");
        underTest.setSenderId(45);

        //act
        String actual = messageService.getStringMessageDescription(underTest);

        //assert
        String expected = "{\"id\":12,\"subject\":\"Some text\",\"senderId\":45,\"receiverId\":48,\"sendDate\":\"23.05.2017\"}";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getStringMessageDescription_MessageWithNullFiends_ShouldReturnStringDescriptionWithoutNullFields(){
        //arrange
        Message underTest = new Message();
        underTest.setId(12);
        underTest.setReceiverId(null);
        underTest.setSendDate("23.05.2017");
        underTest.setSubject("Some text");
        underTest.setSenderId(45);

        //act
        String actual = messageService.getStringMessageDescription(underTest);

        //assert
        String expected = "{\"id\":12,\"subject\":\"Some text\",\"senderId\":45,\"sendDate\":\"23.05.2017\"}";
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void getStringMessageDescription_NullMessage_ShouldThrowNullPointerException(){
        //arrange
        Message underTest = null;

        //act
        String actual = messageService.getStringMessageDescription(underTest);
    }

    @Test
    public void findAllMessages_CorrectUserId_ShouldReturnMessageList(){
        //arrange
        Message message1 = new Message();
        message1.setId(null);
        message1.setReceiverId(receiver.getId());
        message1.setSendDate("2017-05-24 12:45:12.0");
        message1.setSubject("message 1");
        message1.setSenderId(sender.getId());

        Message message2 = new Message();
        message2.setId(null);
        message2.setReceiverId(receiver.getId());
        message2.setSendDate("2017-05-24 12:45:12.0");
        message2.setSubject("message 2");
        message2.setSenderId(sender.getId());

        Message message3 = new Message();
        message3.setId(null);
        message3.setReceiverId(receiver.getId());
        message3.setSendDate("2017-05-24 12:45:12.0");
        message3.setSubject("message 3");
        message3.setSenderId(sender.getId());

        message1 = messageRepository.save(message1);
        message2 = messageRepository.save(message2);
        message3 = messageRepository.save(message3);

        //act
        List<Message> actual = messageService.findAllMessages(sender.getId());

        //clean
        messageRepository.delete(message1);
        messageRepository.delete(message2);
        messageRepository.delete(message3);

        //assert
        Assert.assertThat(actual, containsInAnyOrder(message1, message2, message3));
    }

    @Test(expected = NullPointerException.class)
    public void findAllMessages_NullUserId_ShouldThrowNullPointerException(){
        //arrange
        Integer userId = null;

        //act
        List<Message> actual = messageService.findAllMessages(userId);
    }


    @Test
    public void findAllMessages_NotExistingUserId_ShouldReturnEmptyList(){
        //arrange
        Integer userId = 123456789;

        //act
        List<Message> actual = messageService.findAllMessages(userId);

        //assert
        Assert.assertThat(actual, IsEmptyCollection.empty());
    }

    @Test
    public void findAllMessagesAsMap_CorrectUserId_ShouldReturnMessageMap(){
        //arrange
        Message message1 = new Message();
        message1.setId(null);
        message1.setReceiverId(receiver.getId());
        message1.setSendDate("2017-05-24 12:45:12.0");
        message1.setSubject("message 1");
        message1.setSenderId(sender.getId());

        Message message2 = new Message();
        message2.setId(null);
        message2.setReceiverId(receiver.getId());
        message2.setSendDate("2017-05-24 12:45:12.0");
        message2.setSubject("message 2");
        message2.setSenderId(sender.getId());

        Message message3 = new Message();
        message3.setId(null);
        message3.setReceiverId(receiver.getId());
        message3.setSendDate("2017-05-24 12:45:12.0");
        message3.setSubject("message 3");
        message3.setSenderId(sender.getId());

        message1 = messageRepository.save(message1);
        message2 = messageRepository.save(message2);
        message3 = messageRepository.save(message3);

        //act
        Map<Integer, MessagesDTOObject> actual = messageService.findAllMessagesAsMap(sender.getId());

        //clean
        messageRepository.delete(message1);
        messageRepository.delete(message2);
        messageRepository.delete(message3);

        //assert
        Assert.assertThat(actual.get(receiver.getId()).getMessageList(), containsInAnyOrder(message1, message2, message3));
    }

    @Test(expected = NullPointerException.class)
    public void findAllMessagesAsMap_NullUserId_ShouldThrowNullPointerException(){
        //arrange
        Integer underTest = null;

        //act
        Map<Integer, MessagesDTOObject> actual = messageService.findAllMessagesAsMap(underTest);
    }

    @Test
    public void findAllMessagesAsMap_NotExistingUserId_ShouldReturnCollectionWithoutKey(){
        //arrange
        Integer underTest = 123456789;

        //act
        Map<Integer, MessagesDTOObject> actual = messageService.findAllMessagesAsMap(underTest);

        //assert
        Assert.assertFalse(actual.containsKey(underTest));
    }

}
