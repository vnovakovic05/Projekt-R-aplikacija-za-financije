package hr.osobnefinancije.persistence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;

@Component
public class RecurringTransactionScheduler {

    private static final System.Logger LOGGER =
            System.getLogger(RecurringTransactionScheduler.class.getName());

    private final RecurringTransactionGenerator generator;
    private final Clock clock;

    public RecurringTransactionScheduler(RecurringTransactionGenerator generator, Clock clock) {
        this.generator = generator;
        this.clock = clock;
    }

    @Scheduled(
            cron = "${finance.recurring.cron:0 0 2 * * *}",
            zone = "${finance.recurring.zone:Europe/Zagreb}")
    public void generateDueTransactions() {
        int generated = generator.generateDueTransactions(LocalDate.now(clock));
        if (generated > 0) {
            LOGGER.log(System.Logger.Level.INFO, "Generated {0} recurring transactions", generated);
        }
    }
}
