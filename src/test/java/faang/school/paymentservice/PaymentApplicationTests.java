package faang.school.paymentservice;

import faang.school.paymentservice.client.OpenExchangeRatesClient;
import faang.school.paymentservice.dto.*;
import faang.school.paymentservice.service.PaymentService;
import faang.school.paymentservice.service.exception.CurrencyNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentApplicationTests {

    private String app_id;

    @Mock
    OpenExchangeRatesClient openExchangeRatesClient;
    @InjectMocks
    PaymentService paymentService;

    @Test
    public void testCurrencyExchangeIncorrectRate() {
        when(openExchangeRatesClient.getExchangeRates(app_id, Currency.USD)).thenReturn(getExchangeRates());
        PaymentRequest paymentRequest = new PaymentRequest(1, new BigDecimal(40), Currency.USD);

        assertThrows(CurrencyNotFoundException.class, () -> paymentService.currencyExchange(paymentRequest));
    }

    @Test
    public void testCurrencyExchangeComplete() {
        PaymentRequest paymentRequest = new PaymentRequest(1, new BigDecimal(40), Currency.EUR);

        when(openExchangeRatesClient.getExchangeRates(app_id, Currency.EUR)).thenReturn(getExchangeRates());

        BigDecimal valueExpected = BigDecimal.valueOf(40);
        assertEquals(valueExpected, paymentService.currencyExchange(paymentRequest).amount());
    }

    private ExchangeRates getExchangeRates() {
        ExchangeRates exchangeRates = new ExchangeRates();
        exchangeRates.setRates(Map.of("EUR", BigDecimal.valueOf(10)));
        return exchangeRates;
    }
}
