package com.gagan.expensetracker.controller;

import com.gagan.expensetracker.dto.ExpenseRequest;
import com.gagan.expensetracker.dto.ExpenseResponse;
import com.gagan.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    private Long getCurrentUserId() {
        return 1L;
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> addExpense(@Valid @RequestBody ExpenseRequest req) {
        return ResponseEntity.ok(expenseService.create(getCurrentUserId(), req));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.list(getCurrentUserId()));
    }

    @GetMapping("/month")
    public ResponseEntity<List<ExpenseResponse>> getMonthlyExpenses(@RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(expenseService.listMonthly(getCurrentUserId(), year, month));
    }
}
