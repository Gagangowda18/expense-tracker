package com.gagan.expensetracker.repository;

import com.gagan.expensetracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Fetch all categories of a user
    List<Category> findByUser_Id(Long userId);

    // For auto-create logic (name-based lookup)
    Optional<Category> findByUserIdAndNameIgnoreCase(Long userId, String name);
}
