package com.sachin.expensemanager.dto.expense;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExpenseResponse {

    private Long id;
    private String title;
    private BigDecimal amount;
    private LocalDate date;
    private String notes;

    private Long categoryId;
    private String categoryName;

    private LocalDateTime createdAt;
}
