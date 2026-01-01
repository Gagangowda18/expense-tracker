package com.gagan.expensetracker.controller;

import com.gagan.expensetracker.dto.IncomeRequest;
import com.gagan.expensetracker.dto.IncomeResponse;
import com.gagan.expensetracker.security.SecurityUtils;
import com.gagan.expensetracker.service.IncomeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    private Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserId();
    }

    @PostMapping
    public ResponseEntity<IncomeResponse> create(@Validated @RequestBody IncomeRequest req) {
        return ResponseEntity.ok(incomeService.create(getCurrentUserId(), req));
    }

    @GetMapping
    public ResponseEntity<List<IncomeResponse>> list() {
        return ResponseEntity.ok(incomeService.list(getCurrentUserId()));
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<IncomeResponse>> listMonthly(
            @RequestParam int year,
            @RequestParam int month) {
        return ResponseEntity.ok(incomeService.listMonthly(getCurrentUserId(), year, month));
    }

    // DELETE ALL INCOME for current user
@DeleteMapping("/delete-all")
public ResponseEntity<String> deleteAllIncome() {
    incomeService.deleteAll(getCurrentUserId());
    return ResponseEntity.ok("All income deleted successfully");
}

}
