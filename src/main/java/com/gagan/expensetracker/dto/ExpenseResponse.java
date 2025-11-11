package com.gagan.expensetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseResponse(
        Long id,
        BigDecimal amount,
        LocalDate expenseDate,
        Long categoryId,
        String categoryName,
        String note
) {}
