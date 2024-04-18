package faang.school.paymentservice.service;

import faang.school.paymentservice.client.OpenExchangeRatesClient;
import faang.school.paymentservice.dto.ExchangeRates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import feign.FeignException;


@EnableScheduling
@RequiredArgsConstructor
@Component
@Slf4j
public class CurrencyRateFetcher {
    private final OpenExchangeRatesClient openExchangeRatesClient;

    @Value("${services.exchange.app-id}")
    private String app_id;

    @Scheduled(cron = "${services.exchange.scheduled}")
    @Retryable(retryFor = FeignException.class)
    public void getExchangeRates() {
        ExchangeRates exchangeRates = openExchangeRatesClient.getAllExchangeRates(app_id);
        log.info("All Exchange Rates received");
    }
}