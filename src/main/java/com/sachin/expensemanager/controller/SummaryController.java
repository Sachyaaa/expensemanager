package com.sachin.expensemanager.controller;

import com.sachin.expensemanager.common.ApiResponse;
import com.sachin.expensemanager.dto.summary.CategorySummaryResponse;
import com.sachin.expensemanager.dto.summary.CombinedMonthlySummaryResponse;
import com.sachin.expensemanager.dto.summary.MonthlySummaryResponse;
import com.sachin.expensemanager.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/summary")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/monthly")
    public ResponseEntity<ApiResponse<MonthlySummaryResponse>> getMonthlySummary(@RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(ApiResponse.success("Monthly summary fetched", summaryService.getMonthlySummary(year, month)));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/category")
    public ResponseEntity<ApiResponse<List<CategorySummaryResponse>>> getCategoryWiseSummary(@RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(ApiResponse.success("Category-wise summary fetched", summaryService.getCategoryWiseSummary(year, month)));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/combined")
    public ResponseEntity<ApiResponse<CombinedMonthlySummaryResponse>> getCombinedSummary(@RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(ApiResponse.success("Combined summary fetched", summaryService.getCombinedSummary(year, month)));
    }
}
