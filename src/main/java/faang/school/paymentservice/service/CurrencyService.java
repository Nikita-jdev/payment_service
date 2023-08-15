package faang.school.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import faang.school.paymentservice.client.ExchangeRateClient;
import faang.school.paymentservice.dto.exchange.CurrencyApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyService {
    private final ExchangeRateClient exchangeRateClient;
    private final TextToJsonObjectConverter converter;
    private final Map<String, Double> currencyRates = new HashMap<>();

    @Retryable(maxAttempts = 2, backoff = @Backoff(delay = 2000))
    public CurrencyApiResponse fetchAndSaveCurrencyData() {
        String responseText = exchangeRateClient.getLatestCurrencyRates();
        CurrencyApiResponse response;

        try {
            response = converter.convert(responseText, CurrencyApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Optional.ofNullable(response)
                .map(CurrencyApiResponse::getValute)
                .ifPresent(valute -> valute.forEach((currencyCode, currencyData) -> {
                    currencyRates.put(currencyData.getCharCode(), currencyData.getValue());
                    currencyRates.forEach((key, value) -> System.out.println(key + ": " + value));
                    log.info("Currency rates fetched and updated.");
                }));

        if (currencyRates.isEmpty()) {
            log.info("Failed to fetch currency rates.");
        }

        return response;
    }

    @Recover
    public CurrencyApiResponse recover(Exception e) {
        return new CurrencyApiResponse();
    }
}
