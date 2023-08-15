package faang.school.paymentservice.dto.exchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrencyMap {
    @JsonProperty("CharCode")
    private String charCode;

    @JsonProperty("Value")
    private double value;
}
