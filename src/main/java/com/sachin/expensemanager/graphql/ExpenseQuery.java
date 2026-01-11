package com.sachin.expensemanager.graphql;

import com.sachin.expensemanager.model.Expense;
import com.sachin.expensemanager.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExpenseQuery {

    private final ExpenseService expenseService;

    @QueryMapping
    public List<Expense> expenses(
            @Argument Integer year,
            @Argument Integer month
    ) {
        if (year != null && month != null) {
            return expenseService.getExpensesByMonth(year, month);
        }
        return expenseService.getAllExpenses();
    }
}
