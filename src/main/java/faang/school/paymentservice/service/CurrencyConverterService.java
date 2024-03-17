package faang.school.paymentservice.service;

import faang.school.paymentservice.dto.Currency;
import faang.school.paymentservice.entity.CostProduct;
import faang.school.paymentservice.repository.CostProductRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyConverterService {
    private final CostProductRedisRepository costProductRedisRepository;
    private final OpenExchangeRatesService openExchangeRatesService;
    private final CostProductService costProductService;

    @Value("${payment.commission}")
    private double commission;

    @Async("executorService")
    public void convert() {
        log.info("Starting currency conversion.");
        costProductService.saveCostProduct();
        Map<Currency, BigDecimal> rates = openExchangeRatesService.getRates();
        List<CostProduct> costProducts = StreamSupport.stream(costProductRedisRepository.findAll().spliterator(), false).toList();
        BigDecimal rubRate = rates.get(Currency.RUB).multiply(BigDecimal.valueOf(commission));
        BigDecimal eurRate = rates.get(Currency.EUR).multiply(BigDecimal.valueOf(commission));
        for (CostProduct product : costProducts) {
            BigDecimal usd = product.getUsd();
            BigDecimal rub = usd.multiply(rubRate.setScale(2, RoundingMode.HALF_UP));
            BigDecimal eur = usd.multiply(eurRate.setScale(2, RoundingMode.HALF_UP));
            product.setRub(rub);
            product.setEur(eur);
            costProductRedisRepository.save(product);
            log.info("Currency conversion completed for product: " + product);
        }
    }
}
