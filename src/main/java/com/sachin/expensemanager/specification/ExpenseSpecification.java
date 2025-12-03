package com.sachin.expensemanager.specification;

import com.sachin.expensemanager.model.Expense;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseSpecification {

    public static Specification<Expense> hasCatefory(Long categoryId) {
        return ((root, query, criteriaBuilder)
                -> categoryId == null ? null
                : criteriaBuilder.equal(root.get("category").get("id"), categoryId));
    }

    public static Specification<Expense> dateBetween(LocalDate fromDate, LocalDate toDate) {
        return ((root, query, criteriaBuilder) -> {
            if (fromDate == null && toDate == null) {
                return null;
            }
            if (fromDate != null && toDate != null) {
                return criteriaBuilder.between(root.get("date"), fromDate, toDate);
            }
            if (fromDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("date"), fromDate);
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("date"), toDate);
        });
    }

    public static Specification<Expense> amountBetween(BigDecimal min, BigDecimal max) {
        return ((root, query, criteriaBuilder) -> {
            if (min == null && max == null) {
                return null;
            }
            if (min != null && max != null) {
                return criteriaBuilder.between(root.get("amount"), min, max);
            }
            if (min != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), min);
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("amount"), max);
        });
    }
}
