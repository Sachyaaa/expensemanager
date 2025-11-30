package com.sachin.expensemanager.service;

import com.sachin.expensemanager.dto.expense.ExpenseRequest;
import com.sachin.expensemanager.dto.expense.ExpenseResponse;
import com.sachin.expensemanager.exception.ResourceNotFoundException;
import com.sachin.expensemanager.model.Category;
import com.sachin.expensemanager.model.Expense;
import com.sachin.expensemanager.repository.CategoryRepository;
import com.sachin.expensemanager.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    private ExpenseResponse toResponse(Expense expense) {
        ExpenseResponse res = new ExpenseResponse();

        res.setId(expense.getId());
        res.setTitle(expense.getTitle());
        res.setAmount(expense.getAmount());
        res.setDate(expense.getDate());
        res.setNotes(expense.getNotes());
        res.setCategoryId(expense.getCategory().getId());
        res.setCategoryName(expense.getCategory().getName());
        res.setCreatedAt(expense.getCreatedAt());

        return res;
    }

    @Override
    public ExpenseResponse creteExpense(ExpenseRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Expense expense = new Expense();
        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setDate(request.getDate());
        expense.setNotes(request.getNotes());
        expense.setCategory(category);

        expenseRepository.save(expense);

        return toResponse(expense);
    }

    @Override
    public ExpenseResponse getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        return toResponse(expense);
    }

    @Override
    public List<ExpenseResponse> getAllExpenses() {
        return expenseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ExpenseResponse updateExpense(Long id, ExpenseRequest request) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setDate(request.getDate());
        expense.setNotes(request.getNotes());
        expense.setCategory(category);

        expenseRepository.save(expense);

        return toResponse(expense);
    }

    @Override
    public void deleteExpense(Long id) {

        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Expense not found");
        }
        expenseRepository.deleteById(id);
    }
}
