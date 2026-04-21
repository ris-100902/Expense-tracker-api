package com.example.expensetracker.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExpensetrackerController {
    @GetMapping("/expenses")
    public String getAllExpenses(){
        return "All";
    }
}