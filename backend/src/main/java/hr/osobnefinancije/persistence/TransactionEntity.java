package hr.osobnefinancije.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "user_id", nullable = false)
    Integer userId;

    @Column(name = "category_id", nullable = false)
    Integer categoryId;

    @Column(nullable = false, precision = 12, scale = 2)
    BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    LocalDate transactionDate;

    @Column(columnDefinition = "text")
    String description;

    @Column(name = "created_at", nullable = false)
    OffsetDateTime createdAt;

    @Column(name = "recurring_rule_id")
    Integer recurringRuleId;

    @Column(name = "occurrence_date")
    LocalDate occurrenceDate;

    protected TransactionEntity() {
    }
}
