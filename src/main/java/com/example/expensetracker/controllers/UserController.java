package com.example.expensetracker.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensetracker.entities.User;
import com.example.expensetracker.services.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getAllUsers() {
        return "all Users";
    }

    @PostMapping("/register")
    public ResponseEntity<User>addUser(@RequestBody User user) {
        User newUser = this.userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}