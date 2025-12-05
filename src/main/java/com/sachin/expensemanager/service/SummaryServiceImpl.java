package com.sachin.expensemanager.service;

import com.sachin.expensemanager.dto.summary.CategorySummaryResponse;
import com.sachin.expensemanager.dto.summary.CombinedMonthlySummaryResponse;
import com.sachin.expensemanager.dto.summary.MonthlySummaryResponse;
import com.sachin.expensemanager.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final ExpenseRepository expenseRepository;


    @Override
    public MonthlySummaryResponse getMonthlySummary(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1);
        return expenseRepository.getMonthlySummary(year, month, start, end);
    }

    @Override
    public List<CategorySummaryResponse> getCategoryWiseSummary(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1);
        return expenseRepository.getCategoryWiseSummary(year, month, start, end);
    }

    @Override
    public CombinedMonthlySummaryResponse getCombinedSummary(int year, int month) {
        MonthlySummaryResponse monthly = getMonthlySummary(year, month);
        List<CategorySummaryResponse> category = getCategoryWiseSummary(year, month);

        return new CombinedMonthlySummaryResponse(monthly, category);
    }
}
