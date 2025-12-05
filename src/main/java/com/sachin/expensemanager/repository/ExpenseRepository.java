package com.sachin.expensemanager.repository;

import com.sachin.expensemanager.dto.summary.CategorySummaryResponse;
import com.sachin.expensemanager.dto.summary.MonthlySummaryResponse;
import com.sachin.expensemanager.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {

    @Query("""
            SELECT new com.sachin.expensemanager.dto.summary.MonthlySummaryResponse(
            :year,
            :month,
            COALESCE(SUM(e.amount)),
            COALESCE(MAX(e.amount)),
            COALESCE(MIN(e.amount)),
            COALESCE(AVG(e.amount)),
            COUNT(e)
            )
            FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month
            """)
    MonthlySummaryResponse getMonthlySummary(int year, int month);

    @Query("""
            SELECT new com.sachin.expensemanager.dto.summary.CategorySummaryResponse(
            e.category.id,
            e.category.name,
            SUM(e.amount),
            COUNT(e)
            )
            FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month
            GROUP BY e.category.id, e.category.name
            """)
    List<CategorySummaryResponse> getCategoryWiseSummary(int year, int month);


}
