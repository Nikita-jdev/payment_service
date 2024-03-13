package faang.school.paymentservice.service;

import faang.school.paymentservice.dto.Currency;
import faang.school.paymentservice.entity.CostProduct;
import faang.school.paymentservice.repository.CostProductRedisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class CurrencyConverterServiceTest {
    @Mock
    private OpenExchangeRatesService openExchangeRatesService;
    @Mock
    private CostProductRedisRepository costProductRedisRepository;
    @InjectMocks
    private CurrencyConverterService currencyConverterService;

    @Test
    void testConvert() {
        Map<Currency, BigDecimal> rates = Map.of(Currency.RUB, BigDecimal.valueOf(1.0), Currency.EUR,
                BigDecimal.valueOf(1.0));
        List<CostProduct> costProducts = List.of(CostProduct.builder().build());


    }
}