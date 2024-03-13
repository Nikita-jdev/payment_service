package faang.school.paymentservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;
@Data
public class CurrencyResponse {
    private Map<Currency, BigDecimal> rates;
}
