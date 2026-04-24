package com.example.expensetracker.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensetracker.entities.Expense;
import com.example.expensetracker.entities.User;
import com.example.expensetracker.services.ExpenseService;

@RestController
@RequestMapping("/api")
public class ExpensetrackerController {
    private final ExpenseService expenseService;

    public ExpensetrackerController(ExpenseService service) {
        this.expenseService = service;
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getAllExpenses(@AuthenticationPrincipal User user){
        List<Expense> allExpenses = this.expenseService.getExpenses(user);
        return ResponseEntity.status(HttpStatus.OK).body(allExpenses);
    }

    @PostMapping("/expenses")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense, @AuthenticationPrincipal User user) {
        Expense newExpense = this.expenseService.addExpense(expense, user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(newExpense);
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Expense> deleteExpense(@AuthenticationPrincipal User user, @RequestParam Integer id) {
        Expense deletedExpense = this.expenseService.deleteExpense(user, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedExpense);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(@AuthenticationPrincipal User user, @RequestParam Integer id, @RequestBody Expense newExpense) {
        Expense updatedExpense = this.expenseService.updateExpense(user, id, newExpense);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedExpense);
    }
}