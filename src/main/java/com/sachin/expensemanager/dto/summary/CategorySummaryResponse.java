package com.sachin.expensemanager.dto.summary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CategorySummaryResponse {
    private Long categoryId;
    private String categoryName;
    private BigDecimal totalAmount;
    private long count;
}
