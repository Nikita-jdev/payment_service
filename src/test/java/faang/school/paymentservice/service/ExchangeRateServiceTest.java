package faang.school.paymentservice.service;

import static org.junit.jupiter.api.Assertions.*;


import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import faang.school.paymentservice.entity.ExchangeRate;
import faang.school.paymentservice.repository.ExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceTest {
    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;
    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Test
    void testGetExchangeRate() {
        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate("USD", "USD", LocalDateTime.now());
        assertEquals(BigDecimal.valueOf(1.0), exchangeRate.getCloseRate());

        ExchangeRate exchangeRate1 = exchangeRateService.getExchangeRate("RUB", "USD", LocalDateTime.now());
        assertEquals(BigDecimal.valueOf(0.01065), exchangeRate1.getCloseRate());

        ExchangeRate exchangeRate2 = exchangeRateService.getExchangeRate("KZT", "USD", LocalDateTime.now());
        assertEquals(BigDecimal.valueOf(0.00225), exchangeRate2.getCloseRate());
    }

    @Test
    void testAlphaVantageInvalid() {
        assertThrows(AlphaVantageException.class,
                () -> AlphaVantage.api().exchangeRate().fetchSync());
    }
}
