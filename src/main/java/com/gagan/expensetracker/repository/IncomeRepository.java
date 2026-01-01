package com.gagan.expensetracker.repository;

import com.gagan.expensetracker.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    boolean existsByTransactionRefId(String refId);

    List<Income> findByUser_Id(Long userId);
    void deleteByUserId(Long userId);

    @Query("""
        SELECT i FROM Income i
        WHERE i.user.id = :userId
          AND i.date BETWEEN :start AND :end
    """)
    List<Income> findByUserIdAndDateRange(Long userId, LocalDate start, LocalDate end);
}
