package com.ilibed.post;

import com.ilibed.user.User;
import com.ilibed.user.UserRepository;
import javafx.geometry.Pos;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.validation.constraints.Null;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PostServiceTests {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPostRepository userPostRepository;

    private User user;
    private Post post;

    @Before
    public void createUser(){
        user = new User();
        user.setFirstName("Sender");
        user.setLastName("Sender");
        user.setEmail("sender@gmail.com");
        user.setPassword("1234567");
        user.setMainPhotoId(null);
        user.setRoleId(2);
        user.setBanned(false);
        user.setCity("");
        user.setCountry("");

        post = new Post();
        post.setText("some post text");
        post.setCreationDate("2017-05-24 12:45:12.0");
        post.setPhotoId(2);

        user = userRepository.save(user);
        post = postRepository.save(post);
    }

    @After
    public void deleteUser(){
        userRepository.delete(user);
        postRepository.delete(post);
    }

    @Test
    public void savePost_CorrectPost_ShouldExistsInDatabase(){
        //arrange
        Post underTest = new Post();
        underTest.setText("some post text");
        underTest.setCreationDate("2017-05-24 12:45:12.0");
        underTest.setPhotoId(1);

        //act
        underTest = postService.savePost(underTest);

        //assert
        Assert.assertTrue(postRepository.exists(underTest.getId()));

        //clean
        postRepository.delete(underTest);
    }

    @Test(expected = NullPointerException.class)
    public void savePost_CorrectPost_ShouldThrowNullPointerException(){
        //arrange
        Post underTest = null;
        //act
        underTest = postService.savePost(underTest);
    }

    @Test
    public void savePost_PostWithNullText_ShouldExistsInDatabase(){
        //arrange
        Post underTest = new Post();
        underTest.setText(null);
        underTest.setCreationDate("2017-05-24 12:45:12.0");
        underTest.setPhotoId(1);

        //act
        underTest = postService.savePost(underTest);

        //assert
        Assert.assertTrue(postRepository.exists(underTest.getId()));

        //clean
        postRepository.delete(underTest);
    }

    @Test
    public void savePost_PostWithNullCreationDate_ShouldExistsInDatabase(){
        //arrange
        Post underTest = new Post();
        underTest.setText("some text");
        underTest.setCreationDate(null);
        underTest.setPhotoId(1);

        //act
        underTest = postService.savePost(underTest);

        //assert
        Assert.assertTrue(postRepository.exists(underTest.getId()));

        //clean
        postRepository.delete(underTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void savePost_PostWithNullPhotoId_ShouldThrowDataIntegrityViolationException(){
        //arrange
        Post underTest = new Post();
        underTest.setText("some text");
        underTest.setCreationDate("2017-05-24 12:45:12.0");
        underTest.setPhotoId(null);

        //act
        underTest = postService.savePost(underTest);
    }

    @Test
    public void saveUserPost_CorrectUserPost_ShouldExistsInDatabase(){
        //arrange
        UserPost underTest = new UserPost();
        underTest.setUserId(user.getId());
        underTest.setOwnerId(user.getId());
        underTest.setPostId(post.getId());

        //act
        underTest = postService.saveUserPost(underTest);

        //assert
        Assert.assertTrue(userPostRepository.exists(underTest.getId()));

        //clean
        userPostRepository.delete(underTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveUserPost_NotExistingUser_ShouldThrowDataIntegrityViolationException(){
        //arrange
        UserPost underTest = new UserPost();
        underTest.setUserId(123456789);
        underTest.setOwnerId(123456789);
        underTest.setPostId(post.getId());

        //act
        underTest = postService.saveUserPost(underTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveUserPost_NotExistingPost_ShouldThrowDataIntegrityViolationException(){
        //arrange
        UserPost underTest = new UserPost();
        underTest.setUserId(user.getId());
        underTest.setOwnerId(user.getId());
        underTest.setPostId(123456789);

        //act
        underTest = postService.saveUserPost(underTest);
    }

    @Test(expected = NullPointerException.class)
    public void saveUserPost_NullParameter_ShouldThrowNullPointerException(){
        //arrange
        UserPost underTest = null;

        //act
        underTest = postService.saveUserPost(underTest);
    }

    @Test
    public void deletePost_ExistingPostId_PostShouldNotExist(){
        //arrange
        Post newPost = new Post();
        newPost.setText("some post text");
        newPost.setCreationDate("2017-05-24 12:45:12.0");
        newPost.setPhotoId(2);

        newPost = postRepository.save(newPost);

        UserPost userPost = new UserPost();
        userPost.setUserId(user.getId());
        userPost.setOwnerId(user.getId());
        userPost.setPostId(newPost.getId());

        userPost = userPostRepository.save(userPost);

        //act
        postService.deletePost(userPost.getId());

        //assert
        Assert.assertFalse(userPostRepository.exists(userPost.getId()));
    }

    @Test
    public void deletePost_NotExistingUserPostId_ShouldReturnSilently(){
        //arrange
        Integer underTest = 123456789;

        //act
        postService.deletePost(underTest);
    }

    @Test(expected = NullPointerException.class)
    public void deletePost_NullParameter_ShouldThrowNullPointerException(){
        //arrange
        Integer underTest = null;

        //act
        postService.deletePost(underTest);
    }
}