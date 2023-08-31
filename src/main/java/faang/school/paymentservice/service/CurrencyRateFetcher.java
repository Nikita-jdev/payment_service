package faang.school.paymentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrencyRateFetcher {
    private final CurrencyService currencyService;

    @Scheduled(cron = "${currency.rate.fetch.cron}")
    public void fetchCurrencyRate() {
        log.info("Fetching currency rate");
        currencyService.fetchAndSaveCurrencyData();
    }
}
