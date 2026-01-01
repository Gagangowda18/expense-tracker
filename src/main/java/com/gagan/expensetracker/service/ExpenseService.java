package com.gagan.expensetracker.service;

import com.gagan.expensetracker.dto.ExpenseRequest;
import com.gagan.expensetracker.dto.ExpenseResponse;
import com.gagan.expensetracker.model.Category;
import com.gagan.expensetracker.model.Expense;
import com.gagan.expensetracker.model.User;
import com.gagan.expensetracker.repository.CategoryRepository;
import com.gagan.expensetracker.repository.ExpenseRepository;
import com.gagan.expensetracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    // Map entity → DTO
    private ExpenseResponse toResponse(Expense e) {
        return new ExpenseResponse(
                e.getId(),
                e.getAmount(),
                e.getExpenseDate(),
                e.getCategory() != null ? e.getCategory().getId() : null,
                e.getCategory() != null ? e.getCategory().getName() : null,
                e.getNote()
        );
    }

    // Create or load category
    private Category getOrCreateCategory(Long userId, Long categoryId, String categoryName) {

        if (categoryId != null) {
            return categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category Not Found"));
        }

        if (categoryName == null || categoryName.isBlank()) {
            categoryName = "Other";
        }

        Optional<Category> existing =
                categoryRepository.findByUserIdAndNameIgnoreCase(userId, categoryName);

        if (existing.isPresent()) return existing.get();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        Category newCategory = Category.builder()
                .name(categoryName)
                .user(user)
                .build();

        return categoryRepository.save(newCategory);
    }

    // Duplicate check
    private void ensureNotDuplicate(String refId) {
        if (refId != null && expenseRepository.existsByTransactionRefId(refId)) {
            throw new IllegalArgumentException("Duplicate Transaction");
        }
    }

    // CREATE
    public ExpenseResponse create(Long userId, ExpenseRequest req) {

        ensureNotDuplicate(req.transactionRefId());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        Category category = getOrCreateCategory(
                userId,
                req.categoryId(),
                req.categoryName()
        );

        Expense expense = Expense.builder()
                .amount(req.amount())
                .expenseDate(req.expenseDate())
                .category(category)
                .note(req.note())
                .user(user)
                .transactionRefId(req.transactionRefId())
                .build();

        return toResponse(expenseRepository.save(expense));
    }

    // LIST ALL
    public List<ExpenseResponse> list(Long userId) {
        return expenseRepository.findByUser_Id(userId)
                .stream().map(this::toResponse).toList();
    }

    // MONTHLY LIST
    public List<ExpenseResponse> listMonthly(Long userId, int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return expenseRepository.findByUserIdAndDateRange(userId, start, end)
                .stream().map(this::toResponse).toList();
    }

    // DELETE SINGLE
    public void delete(Long userId, Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Expense Not Found"));

        if (!expense.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized");
        }

        expenseRepository.delete(expense);
    }

    // DELETE ALL EXPENSES for user
    public void deleteAll(Long userId) {
        expenseRepository.deleteByUserId(userId);
    }

    // UPDATE
    public ExpenseResponse update(Long userId, Long expenseId, ExpenseRequest req) {

        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Expense Not Found"));

        if (!expense.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized");
        }

        Category category = getOrCreateCategory(
                userId,
                req.categoryId(),
                req.categoryName()
        );

        expense.setAmount(req.amount());
        expense.setExpenseDate(req.expenseDate());
        expense.setCategory(category);
        expense.setNote(req.note());

        return toResponse(expenseRepository.save(expense));
    }
}
