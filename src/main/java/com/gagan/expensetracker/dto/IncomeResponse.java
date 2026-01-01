package com.gagan.expensetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IncomeResponse(
        Long id,
        BigDecimal amount,
        LocalDateTime date,
        String source
) {}
