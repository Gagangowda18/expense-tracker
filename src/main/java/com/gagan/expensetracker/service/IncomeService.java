package com.gagan.expensetracker.service;

import com.gagan.expensetracker.dto.IncomeRequest;
import com.gagan.expensetracker.dto.IncomeResponse;
import com.gagan.expensetracker.model.Income;
import com.gagan.expensetracker.model.User;
import com.gagan.expensetracker.repository.IncomeRepository;
import com.gagan.expensetracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;

    private IncomeResponse toResponse(Income income) {
        return new IncomeResponse(
                income.getId(),
                income.getAmount(),
                income.getDate(),
                income.getSource()
        );
    }

    // CREATE
    public IncomeResponse create(Long userId, IncomeRequest req) {

        if (req.transactionRefId() != null &&
                incomeRepository.existsByTransactionRefId(req.transactionRefId())) {
            throw new IllegalArgumentException("Duplicate Income Transaction");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        Income income = Income.builder()
                .amount(req.amount())
                .date(req.date())
                .source(req.source())
                .transactionRefId(req.transactionRefId())
                .user(user)
                .build();

        return toResponse(incomeRepository.save(income));
    }

    // DELETE ALL INCOME for a user
    public void deleteAll(Long userId) {
        incomeRepository.deleteByUserId(userId);
    }

    // LIST ALL
    public List<IncomeResponse> list(Long userId) {
        return incomeRepository.findByUser_Id(userId)
                .stream().map(this::toResponse).toList();
    }

    // MONTHLY LIST
    public List<IncomeResponse> listMonthly(Long userId, int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return incomeRepository.findByUserIdAndDateRange(userId, start, end)
                .stream().map(this::toResponse).toList();
    }
}
