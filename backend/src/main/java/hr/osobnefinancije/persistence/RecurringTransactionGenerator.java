package hr.osobnefinancije.persistence;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class RecurringTransactionGenerator {

    private final RecurringRuleRepository recurringRules;
    private final TransactionRepository transactions;
    private final CategoryRepository categories;
    private final Clock clock;

    public RecurringTransactionGenerator(
            RecurringRuleRepository recurringRules,
            TransactionRepository transactions,
            CategoryRepository categories,
            Clock clock) {
        this.recurringRules = recurringRules;
        this.transactions = transactions;
        this.categories = categories;
        this.clock = clock;
    }

    @Transactional
    public int generateDueTransactions(LocalDate throughDate) {
        List<RecurringRuleEntity> dueRules = recurringRules.findDueRulesForUpdate(throughDate);
        int generated = 0;

        for (RecurringRuleEntity rule : dueRules) {
            CategoryEntity category = categories.findByIdAndUserId(rule.categoryId, rule.userId)
                    .orElseThrow(() -> new IllegalStateException(
                            "Recurring rule " + rule.id + " references a missing category"));
            if (!category.type.equals(rule.categoryType)) {
                throw new IllegalStateException(
                        "Recurring rule " + rule.id + " category type does not match its category");
            }

            while (!rule.nextOccurrence.isAfter(throughDate)
                    && (rule.endDate == null || !rule.nextOccurrence.isAfter(rule.endDate))) {
                LocalDate occurrenceDate = rule.nextOccurrence;
                if (!transactions.existsByRecurringRuleIdAndOccurrenceDate(rule.id, occurrenceDate)) {
                    TransactionEntity transaction = new TransactionEntity();
                    transaction.userId = rule.userId;
                    transaction.categoryId = rule.categoryId;
                    transaction.amount = rule.amount;
                    transaction.transactionDate = occurrenceDate;
                    transaction.description = rule.description;
                    transaction.createdAt = OffsetDateTime.now(clock);
                    transaction.recurringRuleId = rule.id;
                    transaction.occurrenceDate = occurrenceDate;
                    transactions.save(transaction);
                    generated++;
                }

                rule.nextOccurrence = nextDate(rule.frequency, occurrenceDate);
            }

            if (rule.endDate != null && rule.nextOccurrence.isAfter(rule.endDate)) {
                rule.active = false;
            }
        }

        return generated;
    }

    private LocalDate nextDate(RecurrenceFrequency frequency, LocalDate occurrenceDate) {
        return switch (frequency) {
            case DAILY -> occurrenceDate.plusDays(1);
            case MONTHLY -> occurrenceDate.plusMonths(1);
        };
    }
}
