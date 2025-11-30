package com.sachin.expensemanager.service;

import com.sachin.expensemanager.dto.expense.ExpenseRequest;
import com.sachin.expensemanager.dto.expense.ExpenseResponse;
import com.sachin.expensemanager.model.Category;
import com.sachin.expensemanager.model.Expense;
import com.sachin.expensemanager.repository.CategoryRepository;
import com.sachin.expensemanager.repository.ExpenseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Test
    void testCreateExpense(){
        ExpenseRequest req = new ExpenseRequest();
        req.setTitle("Test");
        req.setAmount(new BigDecimal(123));
        req.setDate(LocalDate.now());
        req.setCategoryId(1L);

        Category category = new Category();
        category.setId(1L);
        category.setName("Food");

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Expense saved = new Expense();
        saved.setId(1L);
        saved.setTitle("Test");
        saved.setCategory(category);

        Mockito.when(expenseRepository.save(Mockito.any(Expense.class))).thenReturn(saved);

        ExpenseResponse res = expenseService.creteExpense(req);

        Assertions.assertEquals("Test", res.getTitle());
        Assertions.assertEquals(1L, res.getCategoryId());
    }

}
