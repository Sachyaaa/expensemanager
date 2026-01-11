package com.sachin.expensemanager.graphql;

import com.sachin.expensemanager.dto.expense.ExpenseRequest;
import com.sachin.expensemanager.dto.expense.ExpenseResponse;
import com.sachin.expensemanager.model.Expense;
import com.sachin.expensemanager.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ExpenseMutation {

    private final ExpenseService expenseService;

    @MutationMapping
    public ExpenseResponse createExpense(@Argument @Valid ExpenseRequest input) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            throw new RuntimeException("Only USER can create expense");
        }

        return expenseService.creteExpense(
                input
        );
    }

    @MutationMapping
    public Boolean deleteExpense(@Argument Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new RuntimeException("Admin access required");
        }

        expenseService.deleteExpense(id);
        return true;
    }
}