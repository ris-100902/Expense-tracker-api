package com.example.expensetracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.expensetracker.entities.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {}