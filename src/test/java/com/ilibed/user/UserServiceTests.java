package com.ilibed.user;

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
public class UserServiceTests  {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUserIdByEmail_AdminEmail_ShouldReturnAdminId(){
        //arrange
        String underTest = "ilibed.socialnetwork@gmail.com";

        //act
        int actual = userService.getIdByEmail(underTest);

        //assert
        int expected = 1;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getUserIdByEmail_NullEmail_ShouldReturnNegativeValue(){
        //arrange
        String underTest = null;

        //act
        int actual = userService.getIdByEmail(underTest);

        //assert
        Assert.assertTrue(actual < 0);
    }

    @Test
    public void getUserByEmail_AdminEmail_ShouldReturnAdminUser(){
        //arrange
        String underTest = "ilibed.socialnetwork@gmail.com";

        //act
        User actual = userService.getUserByEmail(underTest);

        //assert
        User expected = new User();
        expected.setId(1);
        expected.setFirstName("Admin");
        expected.setLastName("Admin");
        expected.setEmail("ilibed.socialnetwork@gmail.com");
        expected.setPassword("1234567");
        expected.setMainPhotoId(null);
        expected.setRoleId(1);
        expected.setBanned(false);
        expected.setCity("");
        expected.setCountry("");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getUserByEmail_NullEmail_ShouldReturnNull(){
        //arrange
        String underTest = null;

        //act
        User actual = userService.getUserByEmail(underTest);

        //assert
        Assert.assertNull(actual);
    }

    @Test
    public void getUserById_AdminId_ShouldReturnAdminUser(){
        //arrange
        Integer underTest = 1;

        //act
        User actual = userService.getUserById(underTest);

        //assert
        User expected = new User();
        expected.setId(1);
        expected.setFirstName("Admin");
        expected.setLastName("Admin");
        expected.setEmail("ilibed.socialnetwork@gmail.com");
        expected.setPassword("1234567");
        expected.setMainPhotoId(null);
        expected.setRoleId(1);
        expected.setBanned(false);
        expected.setCity("");
        expected.setCountry("");

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

    @Test
    public void getUserById_NullId_ShouldReturnNull(){
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
        String underTest = "ilibed.socialnetwork@gmail.com";

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
    public void createUser_NullParameter_ShouldThrowException(){
        //arrange
        User underTest = null;

        //act
        userService.createUser(underTest);

    }
}