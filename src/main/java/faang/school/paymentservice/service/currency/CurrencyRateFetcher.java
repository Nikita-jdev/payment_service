package faang.school.paymentservice.service.currency;

import faang.school.paymentservice.client.ExchangeServiceClient;
import faang.school.paymentservice.dto.CurrencyRate;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyRateFetcher {

    private final ExchangeServiceClient exchangeServiceClient;

    @Value("${services.exchange.app-id}")
    private String appId;

    @Scheduled(cron = "${scheduler.currency-fetcher}")
    @Retryable(retryFor = FeignException.class, backoff = @Backoff(delay = 5000, multiplier = 2))
    public void fetchCurrencyRate() {
        CurrencyRate currencyRate = exchangeServiceClient.getAllCurrencyRates(appId);
    }
}
