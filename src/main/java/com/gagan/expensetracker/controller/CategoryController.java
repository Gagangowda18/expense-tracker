package com.gagan.expensetracker.controller;

import com.gagan.expensetracker.dto.CategoryRequest;
import com.gagan.expensetracker.dto.CategoryResponse;
import com.gagan.expensetracker.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    private Long getCurrentUserId() {
        return 1L;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest req) {
        return ResponseEntity.ok(categoryService.create(getCurrentUserId(), req));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.list(getCurrentUserId()));
    }
}
