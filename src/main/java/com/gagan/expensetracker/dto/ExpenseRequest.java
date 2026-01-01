package com.gagan.expensetracker.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

import java.time.LocalDateTime;

public record ExpenseRequest(

        @NotNull @DecimalMin("0.0")
        BigDecimal amount,

        @NotNull
        LocalDateTime expenseDate,

        // categoryId is now OPTIONAL (nullable)
        Long categoryId,

        // If category ID is null → we auto-create using this name
        String categoryName,

        @Size(max = 500)
        String note,

        // Gmail unique message ID — used for duplicate prevention
        String transactionRefId
) {}
