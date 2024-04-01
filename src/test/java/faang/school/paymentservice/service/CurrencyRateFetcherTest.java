package faang.school.paymentservice.service;

import faang.school.paymentservice.client.ExchangeServiceClient;
import faang.school.paymentservice.service.currency.CurrencyRateFetcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CurrencyRateFetcherTest {

    @Mock
    private ExchangeServiceClient exchangeServiceClient;

    @InjectMocks
    private CurrencyRateFetcher currencyRateFetcher;

    @Test
    void fetchCurrencyRate_ValidArgs() {
        currencyRateFetcher.fetchCurrencyRate();

        verify(exchangeServiceClient, times(1)).getAllCurrencyRates(null);
    }
}
