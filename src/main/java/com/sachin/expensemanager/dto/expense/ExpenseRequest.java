package com.sachin.expensemanager.dto.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseRequest {

    @Schema(example = "Lunch", description = "Expense title")
    @NotBlank(message = "Title is required")
    private String title;

    @Schema(example = "250.50", description = "Expense amount")
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Date is required")
    private LocalDate date;

    private String notes;

    @Schema(example = "1", description = "Category ID")
    @NotNull(message = "Category id is required")
    private Long categoryId;
}
