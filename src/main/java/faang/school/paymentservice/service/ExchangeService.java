package faang.school.paymentservice.service;

import faang.school.paymentservice.client.CurrencyFeignClient;
import faang.school.paymentservice.dto.Currency;
import faang.school.paymentservice.dto.CurrencyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeService {
    private final CurrencyFeignClient currencyFeignClient;
    @Value("${payment.exchange-service.api-id}")
    private String api;

    public BigDecimal exchange(Currency from, Currency to) {
        CurrencyResponse rates = currencyFeignClient.getLatestRates(
                api,
                from,
                List.of(to)
        );

        return BigDecimal.valueOf(rates.getRates().get(to));
    }
}
