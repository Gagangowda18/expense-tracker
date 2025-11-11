package com.gagan.expensetracker.service;

import com.gagan.expensetracker.dto.ExpenseRequest;
import com.gagan.expensetracker.dto.ExpenseResponse;
import com.gagan.expensetracker.model.Category;
import com.gagan.expensetracker.model.Expense;
import com.gagan.expensetracker.repository.CategoryRepository;
import com.gagan.expensetracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    private ExpenseResponse toResponse(Expense e) {
        return new ExpenseResponse(
                e.getId(),
                e.getAmount(),
                e.getExpenseDate(),
                (e.getCategory() != null) ? e.getCategory().getId() : null,
                (e.getCategory() != null) ? e.getCategory().getName() : null,
                e.getNote()
        );
    }

    public ExpenseResponse create(Long userId, ExpenseRequest req) {
    Category category = categoryRepository.findById(req.categoryId())
            .orElseThrow(() -> new IllegalArgumentException("Category Not Found"));

    System.out.println("== DEBUG: Category ID: " + category.getId() + ", Category Name: " + category.getName());
    System.out.println("== DEBUG: Expense Amount: " + req.amount());
    System.out.println("== DEBUG: Expense Date: " + req.expenseDate());
    System.out.println("== DEBUG: Note: " + req.note());
    System.out.println("== DEBUG: UserId: " + userId);

    Expense expense = Expense.builder()
            .amount(req.amount())
            .expenseDate(req.expenseDate())
            .category(category)
            .note(req.note())
            .userId(userId != null ? userId : 1L)
            .build();

    System.out.println("== DEBUG: Saving expense...");
    Expense saved = expenseRepository.save(expense);
    System.out.println("== DEBUG: Saved with ID " + saved.getId());

    return toResponse(saved);
}

    public List<ExpenseResponse> list(Long userId) {
        return expenseRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ExpenseResponse> listMonthly(Long userId, int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return expenseRepository.findByUserIdAndDateRange(userId, start, end)
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
