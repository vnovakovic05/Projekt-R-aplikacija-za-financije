package hr.osobnefinancije.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "recurring_rules")
public class RecurringRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "user_id", nullable = false)
    Integer userId;

    @Column(name = "category_id", nullable = false)
    Integer categoryId;

    @Column(name = "category_type", nullable = false, length = 10)
    String categoryType;

    @Column(nullable = false, precision = 12, scale = 2)
    BigDecimal amount;

    @Column(columnDefinition = "text")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    RecurrenceFrequency frequency;

    @Column(name = "start_date", nullable = false)
    LocalDate startDate;

    @Column(name = "next_occurrence", nullable = false)
    LocalDate nextOccurrence;

    @Column(name = "end_date")
    LocalDate endDate;

    @Column(nullable = false)
    boolean active;

    @Column(name = "created_at", nullable = false)
    OffsetDateTime createdAt;

    protected RecurringRuleEntity() {
    }
}
