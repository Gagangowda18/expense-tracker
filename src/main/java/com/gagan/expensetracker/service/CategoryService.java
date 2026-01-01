package com.gagan.expensetracker.service;

import com.gagan.expensetracker.dto.CategoryRequest;
import com.gagan.expensetracker.dto.CategoryResponse;
import com.gagan.expensetracker.model.Category;
import com.gagan.expensetracker.model.User;
import com.gagan.expensetracker.repository.CategoryRepository;
import com.gagan.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    // CREATE CATEGORY FOR A USER
    public CategoryResponse create(Long userId, CategoryRequest req) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        Category category = Category.builder()
                .name(req.name())
                .user(user)
                .build();

        Category saved = categoryRepository.save(category);

        return new CategoryResponse(saved.getId(), saved.getName());
    }

    // LIST ONLY USER'S CATEGORIES
    public List<CategoryResponse> list(Long userId) {
        return categoryRepository.findByUser_Id(userId)
                .stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                .toList();
    }

    // DELETE CATEGORY
    public void delete(Long userId, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category Not Found"));

        // ensure category belongs to user
        if (!category.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized: You cannot delete this category");
        }

        categoryRepository.delete(category);
    }

    // UPDATE CATEGORY NAME
    public CategoryResponse update(Long userId, Long categoryId, CategoryRequest req) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category Not Found"));

        // ensure category belongs to user
        if (!category.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized: You cannot edit this category");
        }

        category.setName(req.name());
        Category saved = categoryRepository.save(category);

        return new CategoryResponse(saved.getId(), saved.getName());
    }
}
