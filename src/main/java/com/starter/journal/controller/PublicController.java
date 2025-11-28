package com.starter.journal.controller;

import com.starter.journal.entity.User;
import com.starter.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("health")
    public String HealthCheck(){
        return "Healthy";
    }

    @PostMapping("create-user")
    public User createUser(@RequestBody User user) {
        userService.saveNewUser(user);
        return user;

    }
}
