package hr.osobnefinancije.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "budgets")
public class BudgetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "user_id", nullable = false)
    Integer userId;

    @Column(name = "category_id", nullable = false)
    Integer categoryId;

    @Column(name = "category_type", nullable = false, insertable = false, updatable = false, length = 10)
    String categoryType;

    @Column(nullable = false, precision = 12, scale = 2)
    BigDecimal amount;

    @Column(name = "budget_month", nullable = false)
    LocalDate budgetMonth;

    protected BudgetEntity() {
    }
}
