package hr.osobnefinancije.persistence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class FinanceTimeConfiguration {

    @Bean
    Clock financeClock(@Value("${finance.recurring.zone:Europe/Zagreb}") String zone) {
        return Clock.system(ZoneId.of(zone));
    }
}
