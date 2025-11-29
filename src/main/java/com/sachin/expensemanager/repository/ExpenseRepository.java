package com.sachin.expensemanager.repository;

import com.sachin.expensemanager.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByCategoryId(Long categoryId);
    List<Expense> findByDateBetween(LocalDate start, LocalDate end);
}
