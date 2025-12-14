package com.sachin.expensemanager.service;

import com.sachin.expensemanager.dto.common.PagedResponse;
import com.sachin.expensemanager.dto.expense.ExpenseRequest;
import com.sachin.expensemanager.dto.expense.ExpenseResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    ExpenseResponse creteExpense(ExpenseRequest request);

    ExpenseResponse getExpenseById(Long id);

    PagedResponse<ExpenseResponse> getExpenses(int page, int size, String sortBy, String direction);

    PagedResponse<ExpenseResponse> filterExpenses(Integer page, Integer size, String sortBy, String direction, Long categoryId, LocalDate fromDate, LocalDate toDate, BigDecimal minAmount, BigDecimal maxAmount);

    ExpenseResponse updateExpense(Long id, ExpenseRequest request);

    void deleteExpense(Long id);

    List<ExpenseResponse> getAllExpensesOptimized();

}
