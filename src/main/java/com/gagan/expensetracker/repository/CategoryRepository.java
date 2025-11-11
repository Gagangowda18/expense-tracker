package com.gagan.expensetracker.repository;


import com.gagan.expensetracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {
List<Category> findByUserIdIsNullOrUserId(Long userId);
}