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
@Table(name = "goals")
public class GoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "user_id", nullable = false)
    Integer userId;

    @Column(nullable = false, length = 100)
    String name;

    @Column(name = "target_amount", nullable = false, precision = 12, scale = 2)
    BigDecimal targetAmount;

    @Column(name = "target_date")
    LocalDate targetDate;

    @Column(name = "created_at", nullable = false)
    OffsetDateTime createdAt;

    protected GoalEntity() {
    }
}
