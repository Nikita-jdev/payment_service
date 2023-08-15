package faang.school.paymentservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyRateFetcher {
    private final CurrencyService currencyService;

    @Scheduled(cron = "${currency.rate.fetch.cron}")
    public void fetchCurrencyRate() {
        System.out.println("Fetching currency rate");
        currencyService.fetchAndSaveCurrencyData();
    }
}
