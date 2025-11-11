package com.gagan.expensetracker.service;

import com.gagan.expensetracker.dto.CategoryRequest;
import com.gagan.expensetracker.dto.CategoryResponse;
import com.gagan.expensetracker.model.Category;
import com.gagan.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse create(Long userId, CategoryRequest req) {
        // Create and persist the Category entity
        Category category = new Category();
        category.setName(req.name());
        category.setUserId(userId != null ? userId : 1L); // fallback if userId is null

        Category saved = categoryRepository.save(category);

        // Return the DTO
        return new CategoryResponse(saved.getId(), saved.getName());
    }

    public List<CategoryResponse> list(Long userId) {
        return categoryRepository.findAll()
                .stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                .toList();
    }
}
