package com.sachin.expensemanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="expenses", indexes ={
        @Index(name = "idx_expense_date", columnList = "date"),
        @Index(name = "idx_expense_category", columnList = "category_id")
})
@Getter
@Setter
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate date;

    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }
}
