package com.ilibed.user;

import org.springframework.beans.factory.annotation.Autowired;
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
    public User getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }
}
