package com.sachin.expensemanager.controller;

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
    public ResponseEntity<ExpenseResponse> create(@Valid @RequestBody ExpenseRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.creteExpense(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAll(){
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> update(@PathVariable Long id, @Valid @RequestBody ExpenseRequest request){
        return ResponseEntity.ok(expenseService.updateExpense(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
