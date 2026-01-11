package com.sachin.expensemanager.graphql;

import com.sachin.expensemanager.model.Category;
import com.sachin.expensemanager.model.Expense;
import com.sachin.expensemanager.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ExpenseCategoryBatchResolver {

    private final CategoryRepository categoryRepository;

    @BatchMapping(typeName = "Expense", field = "category")
    public Map<Expense, Category> category(List<Expense> expenses) {

        List<Long> categoryIds = expenses.stream()
                .map(e -> e.getCategory().getId())
                .distinct()
                .toList();

        Map<Long, Category> categoryMap = categoryRepository.findAllById(categoryIds)
                .stream()
                .collect(Collectors.toMap(Category::getId, c -> c));

        return expenses.stream()
                .collect(Collectors.toMap(
                        e -> e,
                        e -> categoryMap.get(e.getCategory().getId())
                ));
    }
}