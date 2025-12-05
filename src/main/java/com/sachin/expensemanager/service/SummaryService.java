package com.sachin.expensemanager.service;

import com.sachin.expensemanager.dto.summary.CategorySummaryResponse;
import com.sachin.expensemanager.dto.summary.CombinedMonthlySummaryResponse;
import com.sachin.expensemanager.dto.summary.MonthlySummaryResponse;

import java.util.List;

public interface SummaryService {
    MonthlySummaryResponse getMonthlySummary(int year, int month);

    List<CategorySummaryResponse> getCategoryWiseSummary(int year, int month);

    CombinedMonthlySummaryResponse getCombinedSummary(int year, int month);

}
