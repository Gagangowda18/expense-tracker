package com.gagan.expensetracker.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "income")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    // ⬅ UPDATED: Store both date + time
    @Column(nullable = false)
    private LocalDateTime date;

    private String source;  // Salary, Cashback, Refund, Bonus

    @Column(name = "transaction_ref_id", unique = true)
    private String transactionRefId; // Gmail message ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = OffsetDateTime.now();
    }
}
