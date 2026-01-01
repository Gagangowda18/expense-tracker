package com.gagan.expensetracker.controller;

import com.gagan.expensetracker.dto.ExpenseRequest;
import com.gagan.expensetracker.dto.ExpenseResponse;
import com.gagan.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gagan.expensetracker.security.SecurityUtils;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor

public class ExpenseController {

    private final ExpenseService expenseService;


    // CREATE EXPENSE
    @PostMapping
    public ResponseEntity<ExpenseResponse> addExpense(@Valid @RequestBody ExpenseRequest req) {
        return ResponseEntity.ok(expenseService.create(getCurrentUserId(), req));
    }

    // LIST ALL EXPENSES FOR USER
    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.list(getCurrentUserId()));
    }

    // LIST MONTHLY EXPENSES FOR USER
    @GetMapping("/month")
    public ResponseEntity<List<ExpenseResponse>> getMonthlyExpenses(
            @RequestParam int year,
            @RequestParam int month
    ) {
        return ResponseEntity.ok(expenseService.listMonthly(getCurrentUserId(), year, month));
    }

    // DELETE EXPENSE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        expenseService.delete(getCurrentUserId(), id);
        return ResponseEntity.ok("Expense deleted successfully");
    }

    // UPDATE EXPENSE
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest req
    ) {
        return ResponseEntity.ok(expenseService.update(getCurrentUserId(), id, req));
    }
    
    // DELETE ALL EXPENSES for current user
@DeleteMapping("/delete-all")
public ResponseEntity<String> deleteAllExpenses() {
    expenseService.deleteAll(getCurrentUserId());
    return ResponseEntity.ok("All expenses deleted successfully");
}

    private Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserId();
    }
}
