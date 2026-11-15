package hr.osobnefinancije.persistence;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecurringTransactionGeneratorTest {

    private final RecurringRuleRepository recurringRules = mock(RecurringRuleRepository.class);
    private final TransactionRepository transactions = mock(TransactionRepository.class);
    private final CategoryRepository categories = mock(CategoryRepository.class);
    private final Clock clock = Clock.fixed(
            Instant.parse("2026-11-15T01:00:00Z"),
            ZoneId.of("Europe/Zagreb"));
    private final RecurringTransactionGenerator generator =
            new RecurringTransactionGenerator(recurringRules, transactions, categories, clock);

    @Test
    void dailyRuleCreatesMissedTransactionsAndAdvancesOneDayAtATime() {
        RecurringRuleEntity rule = rule(RecurrenceFrequency.DAILY, LocalDate.of(2026, 11, 14));
        givenDueRule(rule);

        int generated = generator.generateDueTransactions(LocalDate.of(2026, 11, 15));

        assertEquals(2, generated);
        assertEquals(LocalDate.of(2026, 11, 16), rule.nextOccurrence);
        verify(transactions, times(2)).save(any(TransactionEntity.class));
    }

    @Test
    void monthlyIncomeRuleCreatesTransactionsOnFirstOfEachMissedMonth() {
        RecurringRuleEntity rule = rule(RecurrenceFrequency.MONTHLY, LocalDate.of(2026, 9, 1));
        rule.categoryType = "income";
        givenDueRule(rule);

        int generated = generator.generateDueTransactions(LocalDate.of(2026, 11, 1));

        assertEquals(3, generated);
        assertEquals(LocalDate.of(2026, 12, 1), rule.nextOccurrence);
        verify(transactions, times(3)).save(any(TransactionEntity.class));
    }

    @Test
    void existingOccurrenceIsNotInsertedAgain() {
        RecurringRuleEntity rule = rule(RecurrenceFrequency.DAILY, LocalDate.of(2026, 11, 15));
        givenDueRule(rule);
        when(transactions.existsByRecurringRuleIdAndOccurrenceDate(rule.id, rule.nextOccurrence))
                .thenReturn(true);

        int generated = generator.generateDueTransactions(LocalDate.of(2026, 11, 15));

        assertEquals(0, generated);
        assertEquals(LocalDate.of(2026, 11, 16), rule.nextOccurrence);
        verify(transactions, times(0)).save(any(TransactionEntity.class));
    }

    private void givenDueRule(RecurringRuleEntity rule) {
        CategoryEntity category = new CategoryEntity();
        category.id = rule.categoryId;
        category.userId = rule.userId;
        category.type = rule.categoryType;
        when(recurringRules.findDueRulesForUpdate(any(LocalDate.class))).thenReturn(List.of(rule));
        when(categories.findByIdAndUserId(rule.categoryId, rule.userId)).thenReturn(Optional.of(category));
        when(transactions.existsByRecurringRuleIdAndOccurrenceDate(any(Integer.class), any(LocalDate.class)))
                .thenReturn(false);
    }

    private RecurringRuleEntity rule(RecurrenceFrequency frequency, LocalDate nextOccurrence) {
        RecurringRuleEntity rule = new RecurringRuleEntity();
        rule.id = 1;
        rule.userId = 2;
        rule.categoryId = 3;
        rule.categoryType = "expense";
        rule.amount = new BigDecimal("12.34");
        rule.frequency = frequency;
        rule.startDate = nextOccurrence;
        rule.nextOccurrence = nextOccurrence;
        rule.active = true;
        return rule;
    }
}
