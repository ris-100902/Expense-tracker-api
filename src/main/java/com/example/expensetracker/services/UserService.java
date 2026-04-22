package com.example.expensetracker.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.expensetracker.entities.User;
import com.example.expensetracker.repositories.UserRepository;

@Service
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder encoder, JwtService service) {
        this.userRepository = userRepository;
        this.passwordEncoder = encoder;
        this.jwtService = service;
    }

    public User addUser(User user) {
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);
        return newUser;
    }

    public String loginUser(User inputUser) {
        String inputEmail = inputUser.getEmail();
        User fetchedUser = this.userRepository.findByEmail(inputEmail).orElseThrow();
        if (!passwordEncoder.matches(inputUser.getPassword(), fetchedUser.getPassword())) {
            throw new RuntimeException("Invalid Credentials");
        }
        return jwtService.generateToken(inputUser);
    }
}