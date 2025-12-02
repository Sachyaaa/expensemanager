package com.sachin.expensemanager.service;

import com.sachin.expensemanager.dto.common.PagedResponse;
import com.sachin.expensemanager.dto.expense.ExpenseRequest;
import com.sachin.expensemanager.dto.expense.ExpenseResponse;
import com.sachin.expensemanager.exception.ResourceNotFoundException;
import com.sachin.expensemanager.model.Category;
import com.sachin.expensemanager.model.Expense;
import com.sachin.expensemanager.repository.CategoryRepository;
import com.sachin.expensemanager.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + request.getCategoryId() + " not found"));

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
                .orElseThrow(() -> new ResourceNotFoundException("Expense with id " + id + " not found"));

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
    public PagedResponse<ExpenseResponse> getExpenses(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Expense> expensePage = expenseRepository.findAll(pageable);

        List<ExpenseResponse> dtos = expensePage.getContent().stream()
                .map(this::toResponse)
                .toList();

        PagedResponse<ExpenseResponse> response = new PagedResponse<>();

        response.setContent(dtos);
        response.setPageNumber(expensePage.getNumber());
        response.setPageSize(expensePage.getSize());
        response.setTotalElements(expensePage.getTotalElements());
        response.setTotalPages(expensePage.getTotalPages());
        response.setLast(expensePage.isLast());

        return response;
    }

    @Override
    public ExpenseResponse updateExpense(Long id, ExpenseRequest request) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense with id " + id + " not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + request.getCategoryId() + " not found"));

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
            throw new ResourceNotFoundException("Expense with id " + id + " not found");
        }
        expenseRepository.deleteById(id);
    }
}
