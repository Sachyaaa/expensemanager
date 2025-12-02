package com.sachin.expensemanager.service;

import com.sachin.expensemanager.dto.common.PagedResponse;
import com.sachin.expensemanager.dto.expense.ExpenseRequest;
import com.sachin.expensemanager.dto.expense.ExpenseResponse;

import java.util.List;

public interface ExpenseService {
    ExpenseResponse creteExpense(ExpenseRequest request);

    ExpenseResponse getExpenseById(Long id);

    List<ExpenseResponse> getAllExpenses();

    PagedResponse<ExpenseResponse> getExpenses(int page, int size, String sortBy, String direction);

    ExpenseResponse updateExpense(Long id, ExpenseRequest request);

    void deleteExpense(Long id);

}
