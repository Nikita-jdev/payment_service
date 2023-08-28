package faang.school.paymentservice.controller;

import faang.school.paymentservice.dto.exchange.CurrencyApiResponse;
import faang.school.paymentservice.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/currency")
@RequiredArgsConstructor
public class CurrencyExchangeController {
    private final CurrencyService currencyService;

    @GetMapping("/fetch-currency")
    public ResponseEntity<CurrencyApiResponse> fetchCurrencyData() {
        CurrencyApiResponse response = currencyService.fetchAndSaveCurrencyData();
        if (response == null) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        return ResponseEntity.ok(response);
    }
}
