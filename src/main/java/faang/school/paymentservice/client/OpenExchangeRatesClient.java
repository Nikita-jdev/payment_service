package faang.school.paymentservice.client;

import faang.school.paymentservice.dto.Currency;
import faang.school.paymentservice.dto.ExchangeRates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "OpenExchangeRates", url = "${services.exchange.url}")
public interface OpenExchangeRatesClient {
    @GetMapping("${services.exchange.rates-endpoint}")
    ExchangeRates getExchangeRates(@RequestParam("app_id") String appId,
                                   @RequestParam("symbols") Currency to);
    @GetMapping("${services.exchange.rates-endpoint}")
    ExchangeRates getAllExchangeRates(@RequestParam("app_id") String appId);
}