package com.ilibed.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/api/user/{id}")
    @ResponseBody
    public User getUser(@PathVariable Integer id) {
        return userService.getUserById(id);
    }
}
