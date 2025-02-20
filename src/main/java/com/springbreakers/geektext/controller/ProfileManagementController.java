package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.springbreakers.geektext.model.User;

@RestController
@RequestMapping("/api/users")
public class ProfileManagementController {

    private final UserService userService;

    @Autowired
    public ProfileManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    /*
    TODO:
    - profile management stuff
     */
}
