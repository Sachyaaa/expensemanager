package com.sachin.expensemanager.service;

import com.sachin.expensemanager.dto.summary.CategorySummaryResponse;
import com.sachin.expensemanager.dto.summary.CombinedMonthlySummaryResponse;
import com.sachin.expensemanager.dto.summary.MonthlySummaryResponse;
import com.sachin.expensemanager.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final ExpenseRepository expenseRepository;


    @Override
    public MonthlySummaryResponse getMonthlySummary(int year, int month) {
        return expenseRepository.getMonthlySummary(year, month);
    }

    @Override
    public List<CategorySummaryResponse> getCategoryWiseSummary(int year, int month) {
        return expenseRepository.getCategoryWiseSummary(year, month);
    }

    @Override
    public CombinedMonthlySummaryResponse getCombinedSummary(int year, int month) {
        MonthlySummaryResponse monthly = getMonthlySummary(year, month);
        List<CategorySummaryResponse> category = getCategoryWiseSummary(year, month);

        return new CombinedMonthlySummaryResponse(monthly, category);
    }
}
