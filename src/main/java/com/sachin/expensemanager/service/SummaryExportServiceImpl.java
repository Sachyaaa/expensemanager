package com.sachin.expensemanager.service;

import com.sachin.expensemanager.dto.summary.CategorySummaryResponse;
import com.sachin.expensemanager.dto.summary.CombinedMonthlySummaryResponse;
import com.sachin.expensemanager.dto.summary.MonthlySummaryResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class SummaryExportServiceImpl implements SummaryExportService {

    private final SummaryService summaryService;

    @Override
    public byte[] exportCombinedSummaryCsv(int year, int month) {

        CombinedMonthlySummaryResponse summary =
                summaryService.getCombinedSummary(year, month);

        StringBuilder csv = new StringBuilder();

        // Monthly summary section
        MonthlySummaryResponse m = summary.getMonthlySummaryResponse();

        csv.append("Year,Month,Total Spent,Highest,Lowest,Average,Total Count\n");
        csv.append(m.getYear()).append(",")
                .append(m.getMonth()).append(",")
                .append(m.getTotalSpent()).append(",")
                .append(m.getHighestExpense()).append(",")
                .append(m.getLowestExpense()).append(",")
                .append(m.getAverageExpense()).append(",")
                .append(m.getTotalCount()).append("\n\n");

        // Category summary section
        csv.append("Category,Total Amount,Count\n");

        for (CategorySummaryResponse c : summary.getCategorySummary()) {
            csv.append(c.getCategoryName()).append(",")
                    .append(c.getTotalAmount()).append(",")
                    .append(c.getCount()).append("\n");
        }

        return csv.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] exportCombinedSummaryExcel(int year, int month) {

        CombinedMonthlySummaryResponse summary =
                summaryService.getCombinedSummary(year, month);

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Monthly Summary");

            int rowIdx = 0;

            // Monthly summary header
            Row header = sheet.createRow(rowIdx++);
            header.createCell(0).setCellValue("Year");
            header.createCell(1).setCellValue("Month");
            header.createCell(2).setCellValue("Total Spent");
            header.createCell(3).setCellValue("Highest");
            header.createCell(4).setCellValue("Lowest");
            header.createCell(5).setCellValue("Average");
            header.createCell(6).setCellValue("Total Count");

            MonthlySummaryResponse m = summary.getMonthlySummaryResponse();

            Row data = sheet.createRow(rowIdx++);
            data.createCell(0).setCellValue(m.getYear());
            data.createCell(1).setCellValue(m.getMonth());
            data.createCell(2).setCellValue(m.getTotalSpent().doubleValue());
            data.createCell(3).setCellValue(m.getHighestExpense().doubleValue());
            data.createCell(4).setCellValue(m.getLowestExpense().doubleValue());
            data.createCell(5).setCellValue(m.getAverageExpense().doubleValue());
            data.createCell(6).setCellValue(m.getTotalCount());

            rowIdx++; // blank row

            // Category summary header
            Row catHeader = sheet.createRow(rowIdx++);
            catHeader.createCell(0).setCellValue("Category");
            catHeader.createCell(1).setCellValue("Total Amount");
            catHeader.createCell(2).setCellValue("Count");

            for (CategorySummaryResponse c : summary.getCategorySummary()) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(c.getCategoryName());
                row.createCell(1).setCellValue(c.getTotalAmount().doubleValue());
                row.createCell(2).setCellValue(c.getCount());
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Excel export failed", e);
        }
    }
}
