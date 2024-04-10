package faang.school.paymentservice;

import faang.school.paymentservice.client.OpenExchangeRatesClient;
import faang.school.paymentservice.service.CurrencyRateFetcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CurrencyRateFetcherTest {
    @Mock
    private OpenExchangeRatesClient openExchangeRatesClient;
    @InjectMocks
    private CurrencyRateFetcher currencyRateFetcher;

    @Value("${services.exchange.app-id}")
    private String app_id;

    @Test
    public void gettingTheExchangeRateValidTest() {
        currencyRateFetcher.getExchangeRates();
        verify(openExchangeRatesClient, times(1)).getAllExchangeRates(app_id);
    }
}
