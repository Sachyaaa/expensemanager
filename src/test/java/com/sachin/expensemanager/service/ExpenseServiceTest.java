package com.sachin.expensemanager.service;

import com.sachin.expensemanager.dto.expense.ExpenseRequest;
import com.sachin.expensemanager.dto.expense.ExpenseResponse;
import com.sachin.expensemanager.exception.ResourceNotFoundException;
import com.sachin.expensemanager.model.Category;
import com.sachin.expensemanager.model.Expense;
import com.sachin.expensemanager.model.User;
import com.sachin.expensemanager.repository.CategoryRepository;
import com.sachin.expensemanager.repository.ExpenseRepository;
import com.sachin.expensemanager.security.Util.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SecurityUtil securityUtil;

    private User user;
    private Category category;
    private Expense expense;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(1L)
                .email("user@gmail.com")
                .role("ROLE_USER")
                .build();

        category = Category.builder()
                .id(1L)
                .name("Food")
                .build();

        expense = Expense.builder()
                .id(1L)
                .title("Lunch")
                .amount(BigDecimal.valueOf(200))
                .user(user)
                .category(category)
                .build();
    }

    // ---------------- CREATE ----------------

    @Test
    void shouldCreateExpenseForUser() {

        when(securityUtil.getCurrentUser()).thenReturn(user);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        ExpenseRequest request = ExpenseRequest.builder()
                .title("Lunch")
                .amount(BigDecimal.valueOf(200))
                .categoryId(1L)
                .build();

        ExpenseResponse response = expenseService.creteExpense(request);

        assertNotNull(response);
        assertEquals("Lunch", response.getTitle());

        verify(expenseRepository).save(any(Expense.class));
    }

    // ---------------- GET BY ID ----------------

    @Test
    void shouldAllowUserToFetchOwnExpense() {

        when(securityUtil.getCurrentUser()).thenReturn(user);
        when(securityUtil.isAdmin()).thenReturn(false);
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));

        ExpenseResponse response = expenseService.getExpenseById(1L);

        assertEquals("Lunch", response.getTitle());
    }

    @Test
    void shouldThrowAccessDeniedWhenUserFetchesOthersExpense() {

        User otherUser = User.builder()
                .id(2L)
                .email("other@gmail.com")
                .role("ROLE_USER")
                .build();

        expense.setUser(otherUser);

        when(securityUtil.getCurrentUser()).thenReturn(user);
        when(securityUtil.isAdmin()).thenReturn(false);
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));

        assertThrows(
                AccessDeniedException.class,
                () -> expenseService.getExpenseById(1L)
        );
    }

    // ---------------- UPDATE ----------------

    @Test
    void shouldAllowAdminToUpdateAnyExpense() {

        when(securityUtil.isAdmin()).thenReturn(true);
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(expenseRepository.save(any())).thenReturn(expense);

        ExpenseRequest request = ExpenseRequest.builder()
                .title("Dinner")
                .amount(BigDecimal.valueOf(300))
                .categoryId(1L)
                .build();

        ExpenseResponse response =
                expenseService.updateExpense(1L, request);

        assertEquals("Dinner", response.getTitle());
    }

    // ---------------- DELETE ----------------

    @Test
    void shouldDeleteExpenseForOwner() {

        when(securityUtil.getCurrentUser()).thenReturn(user);
        when(securityUtil.isAdmin()).thenReturn(false);
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));

        expenseService.deleteExpense(1L);

        verify(expenseRepository).deleteById(1L);
    }

    // ---------------- NOT FOUND ----------------

    @Test
    void shouldThrowNotFoundWhenExpenseMissing() {

        when(expenseRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> expenseService.getExpenseById(99L)
        );
    }
}
