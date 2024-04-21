package faang.school.paymentservice.service;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.exchangerate.ExchangeRateResponse;
import faang.school.paymentservice.repository.ExchangeRateRepository;
import faang.school.paymentservice.entity.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService{
    private final ExchangeRateRepository exchangeRateRepository;
    private final String currencyKZT = "KZT";
    private final String currencyRUB = "RUB";
    private final String currencyUSD = "USD";


    @Override
    public ExchangeRate updateExchangeRate(String fromCurrency, String toCurrency, LocalDateTime exchangeDate) {
        try {
            ExchangeRateResponse exchangeRateResponse = AlphaVantage.api()
                    .exchangeRate()
                    .fromCurrency(fromCurrency)
                    .toCurrency(toCurrency)
                    .fetchSync();
            log.info("Exchange rate response is received");
            ExchangeRate exchangeRate = new ExchangeRate(fromCurrency, toCurrency, exchangeDate, exchangeRateResponse.getExchangeRate());
            return exchangeRateRepository.save(exchangeRate);

        } catch (AlphaVantageException e) {
            log.error("Error in receiving the exchange rate");
            return null;
        }
    }

    @Override
    public ExchangeRate getExchangeRate(String sourceCurrency, String targetCurrency, LocalDateTime exchangeDate) {
        if (sourceCurrency.equals("USD")) {
            return new ExchangeRate(targetCurrency, sourceCurrency, exchangeDate, 1);
        } else if (sourceCurrency.equals("RUB")) {
            return new ExchangeRate(targetCurrency, sourceCurrency, exchangeDate, 0.01065);
        } else if (sourceCurrency.equals("KZT")) {
            return new ExchangeRate(targetCurrency, sourceCurrency, exchangeDate, 0.00225);
        }
        return exchangeRateRepository.findByFromCurrencyAndToCurrency(targetCurrency, sourceCurrency)
                .orElseGet(() -> updateExchangeRate(targetCurrency, sourceCurrency, LocalDateTime.now()));
    }

    @PostConstruct
    public void init() {
        Config cfg = Config.builder()
                .key("TSFY1GK4SDY3L5XD")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
    }

    @Scheduled(cron = "${exchange_rate.update}")
    public void scheduledUpdate() {
        updateExchangeRate(currencyKZT, currencyUSD, LocalDateTime.now());
        updateExchangeRate(currencyRUB, currencyUSD, LocalDateTime.now());
    }
}
