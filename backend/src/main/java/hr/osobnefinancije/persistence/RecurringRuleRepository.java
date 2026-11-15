package hr.osobnefinancije.persistence;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RecurringRuleRepository extends JpaRepository<RecurringRuleEntity, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            select rule
            from RecurringRuleEntity rule
            where rule.active = true and rule.nextOccurrence <= :throughDate
            order by rule.id
            """)
    List<RecurringRuleEntity> findDueRulesForUpdate(@Param("throughDate") LocalDate throughDate);
}
