package faang.school.paymentservice.service;

import faang.school.paymentservice.entity.ExchangeRate;

import java.time.LocalDateTime;

public interface ExchangeRateService {
    ExchangeRate updateExchangeRate(String fromCurrency, String toCurrency, LocalDateTime exchangeDate);

    ExchangeRate getExchangeRate(String sourceCurrency, String targetCurrency, LocalDateTime exchangeDate);
}
