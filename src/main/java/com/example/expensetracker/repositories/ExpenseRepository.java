package com.example.expensetracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.expensetracker.entities.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    @Query(value="SELECT * FROM Expense WHERE user_id=:userId", nativeQuery=true)
    List<Expense> getAllExpenses(Integer userId);
}