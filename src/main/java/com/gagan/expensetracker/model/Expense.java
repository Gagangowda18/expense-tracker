package com.gagan.expensetracker.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    // ⬅ UPDATED: store date + time from Gmail
    @Column(nullable = false)
    private LocalDateTime expenseDate;

    // Category Relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String note;

    // User Relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Prevent duplicate Gmail imports
    @Column(unique = true)
    private String transactionRefId;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
