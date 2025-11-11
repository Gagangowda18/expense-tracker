package com.gagan.expensetracker.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseRequest(
        @NotNull @DecimalMin("0.0") BigDecimal amount,
        @NotNull LocalDate expenseDate,
        @NotNull Long categoryId,
        @Size(max = 500) String note
) {}
