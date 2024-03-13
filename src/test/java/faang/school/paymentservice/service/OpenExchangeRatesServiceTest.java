package faang.school.paymentservice.service;

import faang.school.paymentservice.client.OpenExchangeRatesClient;
import faang.school.paymentservice.dto.Currency;
import faang.school.paymentservice.dto.CurrencyResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenExchangeRatesServiceTest {
    @Mock
    private OpenExchangeRatesClient openExchangeRatesClient;
    @InjectMocks
    private OpenExchangeRatesService openExchangeRatesService;


    @Test
    void testGetRates() {
        ReflectionTestUtils.setField(openExchangeRatesService, "appId",
                "ca800d6c1f28496a9461bd842d20b919");
        Map<Currency, BigDecimal> rates = Map.of(Currency.RUB, BigDecimal.valueOf(1.0), Currency.EUR, BigDecimal.valueOf(1.0));
        CurrencyResponse response = CurrencyResponse.builder().rates(rates).build();
        when(openExchangeRatesClient.getRates(anyString(), eq(Currency.USD), anyString())).thenReturn(response);
        Map<Currency, BigDecimal> expectedRates = Map.of(Currency.RUB, BigDecimal.valueOf(1.0), Currency.EUR, BigDecimal.valueOf(1.0));
        assertEquals(expectedRates, openExchangeRatesService.getRates());
    }
}