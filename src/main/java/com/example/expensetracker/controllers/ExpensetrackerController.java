package com.example.expensetracker.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensetracker.entities.Expense;
import com.example.expensetracker.entities.User;

@RestController
@RequestMapping("/api")
public class ExpensetrackerController {
    @GetMapping("/expenses")
    public String getAllExpenses(){
        return "All";
    }

    @PostMapping("/expenses")
    public String addExpense(@RequestBody Expense expense, @AuthenticationPrincipal User user) {
        IO.println("User: "+ user.getEmail());
        return "Added";
    }
}