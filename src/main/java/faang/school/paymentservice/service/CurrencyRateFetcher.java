package faang.school.paymentservice.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CurrencyRateFetcher {
    @Scheduled(cron = "${currency.rate.fetch.cron}")
    public void fetchCurrencyRate() {
        System.out.println("Fetching currency rate");
    }
}
