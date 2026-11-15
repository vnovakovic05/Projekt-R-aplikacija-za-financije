package hr.osobnefinancije.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

    boolean existsByRecurringRuleIdAndOccurrenceDate(Integer recurringRuleId, LocalDate occurrenceDate);
}
