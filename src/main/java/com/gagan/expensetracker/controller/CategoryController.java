package com.gagan.expensetracker.controller;

import com.gagan.expensetracker.dto.CategoryRequest;
import com.gagan.expensetracker.dto.CategoryResponse;
import com.gagan.expensetracker.security.SecurityUtils;
import com.gagan.expensetracker.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gagan.expensetracker.security.SecurityUtils;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor

public class CategoryController {

    private final CategoryService categoryService;



    // CREATE CATEGORY
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest req) {
        return ResponseEntity.ok(categoryService.create(getCurrentUserId(), req));
    }

    // LIST ALL CATEGORIES FOR USER
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.list(getCurrentUserId()));
    }

    // DELETE CATEGORY (Industry Feature)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.delete(getCurrentUserId(), id);
        return ResponseEntity.ok("Category deleted successfully");
    }

    // UPDATE CATEGORY NAME (Optional)
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequest req
    ) {
        return ResponseEntity.ok(categoryService.update(getCurrentUserId(), id, req));
    }

    private Long getCurrentUserId() {
    return SecurityUtils.getCurrentUserId();
    }

}
