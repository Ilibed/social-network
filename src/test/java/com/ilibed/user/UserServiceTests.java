package com.ilibed.user;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserServiceTests  {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void createUser(){
        user = new User();
        user.setFirstName("User");
        user.setLastName("User");
        user.setEmail("user@gmail.com");
        user.setPassword("1234567");
        user.setMainPhotoId(null);
        user.setRoleId(2);
        user.setBanned(false);
        user.setCity("");
        user.setCountry("");

        user = userRepository.save(user);
    }

    @After
    public void deleteUser(){
        userRepository.delete(user);
    }

    @Test
    public void getUserIdByEmail_UserEmail_ShouldReturnUserId(){
        //arrange
        String underTest = user.getEmail();

        //act
        int actual = userService.getIdByEmail(underTest);

        //assert
        int expected = user.getId();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void getUserIdByEmail_NullEmail_ShouldThrowNullPointerException(){
        //arrange
        String underTest = null;

        //act
        int actual = userService.getIdByEmail(underTest);
    }

    @Test
    public void getUserByEmail_UserEmail_ShouldReturnUser(){
        //arrange
        String underTest = user.getEmail();

        //act
        User actual = userService.getUserByEmail(underTest);

        //assert
        User expected = user;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void getUserByEmail_NullEmail_ShouldReturnNull(){
        //arrange
        String underTest = null;

        //act
        User actual = userService.getUserByEmail(underTest);
    }

    @Test
    public void getUserById_UserId_ShouldReturnUser(){
        //arrange
        Integer underTest = user.getId();

        //act
        User actual = userService.getUserById(underTest);

        //assert
        User expected = user;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getUserById_NotExistingId_ShouldReturnNull(){
        //arrange
        Integer underTest = 500000000;

        //act
        User actual = userService.getUserById(underTest);

        //assert
        Assert.assertNull(actual);
    }

    @Test(expected = NullPointerException.class)
    public void getUserById_NullId_ShouldThrowNullPointerException(){
        //arrange
        Integer underTest = null;

        //act
        User actual = userService.getUserById(underTest);

        //assert
        Assert.assertNull(actual);
    }

    @Test
    public void isEmailExists_ExistingEmail_ShouldReturnTrue(){
        //arrange
        String underTest = user.getEmail();

        //act
        boolean actual = userService.isEmailExists(underTest);

        //assert
        Assert.assertTrue(actual);
    }

    @Test
    public void isEmailExists_NotExistingEmail_ShouldReturnFalse(){
        //arrange
        String underTest = "not_existing_email@gmail.com";

        //act
        boolean actual = userService.isEmailExists(underTest);

        //assert
        Assert.assertFalse(actual);
    }

    @Test
    public void isEmailExists_NullEmail_ShouldReturnFalse(){
        //arrange
        String underTest = null;

        //act
        boolean actual = userService.isEmailExists(underTest);

        //assert
        Assert.assertFalse(actual);
    }


    @Test
    public void createUser_NewUser_NewEmailShouldExists(){
        //arrange
        User underTest = new User();
        underTest.setId(null);
        underTest.setFirstName("NotExistingUser");
        underTest.setLastName("NotExistingUser");
        underTest.setEmail("not_existing_email@gmail.com");
        underTest.setPassword("password");
        underTest.setMainPhotoId(null);
        underTest.setRoleId(null);
        underTest.setBanned(null);
        underTest.setCity("Not existing city");
        underTest.setCountry("Not existing country");

        //act
        userService.createUser(underTest);
        String actual = underTest.getEmail();

        //assert
        Assert.assertTrue(userService.isEmailExists(actual));

        //clean
        userRepository.delete(underTest);
    }

    @Test(expected = NullPointerException.class)
    public void createUser_NullParameter_ShouldThrowNullPointerException(){
        //arrange
        User underTest = null;

        //act
        userService.createUser(underTest);
    }
}