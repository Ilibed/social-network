package com.ilibed.user;

import com.ilibed.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/api/user/{id}")
    @ResponseBody
    public PresentationUser getUser(@PathVariable Integer id) {
        return userService.getPresentationUser(id);
    }

    @RequestMapping(value = "/api/user")
    @ResponseBody
    public ResponseEntity<User> createUser(@RequestBody User user){
        if (!userService.isEmailExists(user.getEmail())){
            userService.createUser(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }

        return new ResponseEntity<User>(user, HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/api/user/update")
    @ResponseBody
    public ResponseEntity<User> updateUser(@RequestParam(value = "file") MultipartFile file,
                                           @RequestParam("name") String name,
                                           @RequestParam("surname") String surname,
                                           @RequestParam("country") String country,
                                           @RequestParam("city") String city){
        User user = null;
        try {
            user = userService.updateUser(file, name, surname, country, city);

            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (ServiceException e){
            //logging
            System.out.print(e.getMessage());
        }

        return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("/api/friend/add/{id}")
    @ResponseBody
    public ResponseEntity<SimpleUser> addToFriend(@PathVariable("id") Integer id){
        SimpleUser simpleUser = userService.addToFriend(id);

        if (simpleUser == null){
            return new ResponseEntity<SimpleUser>(simpleUser, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<SimpleUser>(simpleUser, HttpStatus.OK);
    }

    @RequestMapping("/api/friend/remove/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> removeFriend(@PathVariable("id") Integer id){
        userService.removeFriend(id);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @RequestMapping("/api/get/friends")
    @ResponseBody
    public ResponseEntity<List<SimpleUser>> getUserFriends(){
        List<SimpleUser> simpleUsers = userService.getUserFriends();

        if (simpleUsers == null){
            return new ResponseEntity<List<SimpleUser>>(simpleUsers, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<SimpleUser>>(simpleUsers, HttpStatus.OK);
    }

    @RequestMapping("/api/get/users")
    @ResponseBody
    public ResponseEntity<List<SimpleUser>> getUsers(){
        List<SimpleUser> simpleUsers = userService.getUsers();

        if (simpleUsers == null){
            return new ResponseEntity<List<SimpleUser>>(simpleUsers, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<SimpleUser>>(simpleUsers, HttpStatus.OK);
    }

    @RequestMapping("/api/auth")
    public ResponseEntity<User> getAuthUser(){
        User user = userService.getAuthUser();
        if (user == null){
            return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
