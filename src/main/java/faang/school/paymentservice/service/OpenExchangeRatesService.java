package faang.school.paymentservice.service;

import faang.school.paymentservice.client.OpenExchangeRatesClient;
import faang.school.paymentservice.dto.Currency;
import faang.school.paymentservice.dto.CurrencyResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenExchangeRatesService {
    private final OpenExchangeRatesClient openExchangeRatesClient;

    @Value("${open-exchange-rates.app-id}")
    private String appId;

    @Retryable(retryFor = FeignException.class, maxAttempts = 5, backoff = @Backoff(delay = 500))
    public Map<Currency, BigDecimal> getRates() {
        String symbolsParam = String.join(",", List.of(Currency.RUB.toString(), Currency.EUR.toString()));
        CurrencyResponse response = openExchangeRatesClient.getRates(appId, Currency.USD, symbolsParam);
        if (response.getRates().isEmpty()) {
            var message = String.format("Failed to get rates from Open Exchange Rates API. Response: %s", response);
            log.error(message);
            throw new RuntimeException(message);
        }
        return response.getRates();
    }
}
