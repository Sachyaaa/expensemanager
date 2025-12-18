package com.sachin.expensemanager.service;

public interface SummaryExportService {
    byte[] exportCombinedSummaryCsv(int year, int month);

    byte[] exportCombinedSummaryExcel(int year, int month);
}
