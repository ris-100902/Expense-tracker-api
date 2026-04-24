package com.example.expensetracker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.expensetracker.entities.Expense;
import com.example.expensetracker.entities.User;
import com.example.expensetracker.repositories.ExpenseRepository;
import com.example.expensetracker.repositories.UserRepository;

@Service
public class ExpenseService {
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    public ExpenseService(UserRepository userRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpense(Expense expense, String userEmail){
        User inputUser = this.userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("No such User"));
        expense.setUser(inputUser);
        this.expenseRepository.save(expense);
        return expense;
    }

    public List<Expense> getExpenses(User inputUser){
        return this.expenseRepository.getAllExpenses(inputUser.getId());
    }

    public Expense deleteExpense(User inputUser, Integer expenseId) {
        Expense toDeleteExpense = this.expenseRepository.getExpense(inputUser.getId(), expenseId);
        if (toDeleteExpense == null) throw new RuntimeException("No such expense or unauthorized");
        this.expenseRepository.deleteById(expenseId);
        return toDeleteExpense;
    }

    public Expense updateExpense(User inputUser, Integer expenseId, Expense newExpense) {
        Expense toUpdateExpense = this.expenseRepository.getExpense(inputUser.getId(), expenseId);
        if (toUpdateExpense == null) throw new RuntimeException("No such expense or unauthorized");
        toUpdateExpense.setCategory(newExpense.getCategory());
        toUpdateExpense.setCost(newExpense.getCost());
        toUpdateExpense.setTitle(newExpense.getTitle());
        return toUpdateExpense;
    }
}