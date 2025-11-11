package com.gagan.expensetracker.repository;


import com.gagan.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {
List<Expense> findByUserId(Long userId);


@Query("SELECT e FROM Expense e WHERE e.userId = ?1 AND e.expenseDate BETWEEN ?2 AND ?3")
List<Expense> findByUserIdAndDateRange(Long userId, LocalDate start, LocalDate end);
}