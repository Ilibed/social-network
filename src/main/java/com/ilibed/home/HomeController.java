package com.ilibed.home;

import com.ilibed.user.User;
import com.ilibed.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String home(@RequestParam(name = "email", required = false) String email) {
        if (email != null) {
            User user = userService.getUserByEmail(email);
            return user != null ? user.getFirstName() + " " + user.getLastName() : "User not found";
        } else {
            return "Guest";
        }
    }
}
