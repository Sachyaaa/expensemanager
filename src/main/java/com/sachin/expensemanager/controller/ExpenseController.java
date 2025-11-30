package com.sachin.expensemanager.controller;

import com.sachin.expensemanager.common.ApiResponse;
import com.sachin.expensemanager.dto.expense.ExpenseRequest;
import com.sachin.expensemanager.dto.expense.ExpenseResponse;
import com.sachin.expensemanager.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseResponse>> create(@Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse response = expenseService.creteExpense(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Expense created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponse>> getById(@PathVariable Long id) {
        ExpenseResponse response = expenseService.getExpenseById(id);
        return ResponseEntity.ok(ApiResponse.success("Fetched expense", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>> getAll() {
        List<ExpenseResponse> response = expenseService.getAllExpenses();
        return ResponseEntity.ok(ApiResponse.success("All expenses fetched", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponse>> update(@PathVariable Long id, @Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse response = expenseService.updateExpense(id, request);
        return ResponseEntity.ok(ApiResponse.success("Expense updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok(ApiResponse.success("Expense deleted successfully", null));
    }
}
