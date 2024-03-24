package faang.school.paymentservice.service;

import faang.school.paymentservice.dto.Currency;
import faang.school.paymentservice.entity.CostProduct;
import faang.school.paymentservice.repository.CostProductRedisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyConverterServiceTest {
    @Mock
    private OpenExchangeRatesService openExchangeRatesService;
    @Mock
    private CostProductRedisRepository costProductRedisRepository;
    @Mock
    private CostProductService costProductService;
    @InjectMocks
    private CurrencyConverterService currencyConverterService;

    @Test
    void testConvert() {
        Map<Currency, BigDecimal> rates = Map.of(Currency.RUB, BigDecimal.valueOf(1.0),
                Currency.EUR, BigDecimal.valueOf(1.0));
        CostProduct costProduct = CostProduct.builder()
                .usd(BigDecimal.valueOf(1.0))
                .eur(BigDecimal.valueOf(0.0))
                .rub(BigDecimal.valueOf(0.0)).build();
        List<CostProduct> costProducts = List.of(costProduct);
        when(costProductRedisRepository.findAll()).thenReturn(costProducts);
        when(openExchangeRatesService.getRates()).thenReturn(rates);
        ArgumentCaptor<CostProduct> captor = ArgumentCaptor.forClass(CostProduct.class);
        currencyConverterService.convert();
        verify(openExchangeRatesService, times(1)).getRates();
        verify(costProductRedisRepository, times(1)).save(captor.capture());
        CostProduct capturedProduct = captor.getValue();
        assertEquals(costProduct.getRub(), capturedProduct.getRub());
        assertEquals(costProduct.getEur(), capturedProduct.getEur());
        assertEquals(costProduct.getUsd(), capturedProduct.getUsd());
    }
}