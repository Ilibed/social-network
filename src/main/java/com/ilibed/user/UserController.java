package com.ilibed.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/api/auth")
    public ResponseEntity<User> getAuthUser(){
        User user = userService.getAuthUser();
        if (user == null){
            return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
