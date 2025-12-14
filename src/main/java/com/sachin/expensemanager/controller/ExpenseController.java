package com.sachin.expensemanager.controller;

import com.sachin.expensemanager.common.ApiResponse;
import com.sachin.expensemanager.dto.common.PagedResponse;
import com.sachin.expensemanager.dto.expense.ExpenseRequest;
import com.sachin.expensemanager.dto.expense.ExpenseResponse;
import com.sachin.expensemanager.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseResponse>> create(@Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse response = expenseService.creteExpense(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Expense created successfully", response));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponse>> getById(@PathVariable Long id) {
        ExpenseResponse response = expenseService.getExpenseById(id);
        return ResponseEntity.ok(ApiResponse.success("Fetched expense", response));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/optimized")
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>> getAllOptimized() {
        List<ExpenseResponse> response = expenseService.getAllExpensesOptimized();
        return ResponseEntity.ok(ApiResponse.success("All expenses fetched with JOIN FETCH", response));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<PagedResponse<ExpenseResponse>>> getPagedExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        PagedResponse<ExpenseResponse> response = expenseService.getExpenses(page, size, sortBy, direction);

        return ResponseEntity.ok(ApiResponse.success("Expenses Fetched successfully", response));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<PagedResponse<ExpenseResponse>>> filterExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount
    ) {
        PagedResponse<ExpenseResponse> result = expenseService.filterExpenses(page, size, sortBy, direction, categoryId, fromDate, toDate, minAmount, maxAmount);

        return ResponseEntity.ok(ApiResponse.success("Filtered expenses", result));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponse>> update(@PathVariable Long id, @Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse response = expenseService.updateExpense(id, request);
        return ResponseEntity.ok(ApiResponse.success("Expense updated successfully", response));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok(ApiResponse.success("Expense deleted successfully", null));
    }
}
