package com.sachin.expensemanager.dto.summary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CombinedMonthlySummaryResponse {
    private MonthlySummaryResponse monthlySummaryResponse;
    private List<CategorySummaryResponse> categorySummary;
}
