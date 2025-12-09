package com.sachin.expensemanager.repository;

import com.sachin.expensemanager.dto.summary.CategorySummaryResponse;
import com.sachin.expensemanager.dto.summary.MonthlySummaryResponse;
import com.sachin.expensemanager.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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
            FROM Expense e WHERE e.date >= :startDate AND e.date <= :endDate
            """)
    MonthlySummaryResponse getMonthlySummary(@Param("year") int year, @Param("month") int month, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("""
            SELECT new com.sachin.expensemanager.dto.summary.CategorySummaryResponse(
            e.category.id,
            e.category.name,
            SUM(e.amount),
            COUNT(e)
            )
            FROM Expense e WHERE e.date >= :startDate AND e.date <= :endDate
            GROUP BY e.category.id, e.category.name
            """)
    List<CategorySummaryResponse> getCategoryWiseSummary(@Param("year") int year, @Param("month") int month, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("""
            SELECT e FROM Expense e
            JOIN FETCH e.category
            """)
    List<Expense> findAllWithCategory();

    @EntityGraph(attributePaths = "category")
    Page<Expense> findAll(Specification<Expense> spec, Pageable pageable);

    @EntityGraph(attributePaths = "category")
    Page<Expense> findAll(Pageable pageable);

}
