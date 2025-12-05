package com.sachin.expensemanager.dto.summary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MonthlySummaryResponse {
    private int year;
    private int month;
    private BigDecimal totalSpent;
    private BigDecimal highestExpense;
    private BigDecimal lowestExpense;
    private BigDecimal averageExpense;
    private long totalCount;

    public MonthlySummaryResponse(Number year, Number month, BigDecimal totalSpent, BigDecimal highestExpense, BigDecimal lowestExpense, Number averageExpense, Number totalCount){
        this.year = year.intValue();
        this.month = month.intValue();
        this.totalSpent = totalSpent ==null? BigDecimal.ZERO : totalSpent;
        this.highestExpense = highestExpense == null ? BigDecimal.ZERO : highestExpense;
        this.lowestExpense = lowestExpense == null ? BigDecimal.ZERO : lowestExpense;
        this.averageExpense = averageExpense == null ? BigDecimal.ZERO : BigDecimal.valueOf(averageExpense.doubleValue());
        this.totalCount = totalCount == null ? 0L : totalCount.longValue();
    }
}
