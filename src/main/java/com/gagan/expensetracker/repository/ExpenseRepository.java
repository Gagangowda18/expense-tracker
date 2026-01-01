package com.gagan.expensetracker.repository;

import com.gagan.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;



public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUser_Id(Long userId);

    @Query("""
        SELECT e FROM Expense e
        WHERE e.user.id = :userId
          AND e.expenseDate BETWEEN :start AND :end
    """)
    List<Expense> findByUserIdAndDateRange(Long userId, LocalDate start, LocalDate end);
    boolean existsByTransactionRefId(String transactionRefId);
    void deleteByUserId(Long userId);

}
