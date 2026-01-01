package com.gagan.expensetracker.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record IncomeRequest(
        @NotNull BigDecimal amount,
        @NotNull LocalDateTime date,
        String source,
        String transactionRefId
) {}
