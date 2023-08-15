package faang.school.paymentservice.dto.exchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CurrencyApiResponse {
    @JsonProperty("Date")
    private String date;

    @JsonProperty("PreviousDate")
    private String previousDate;

    @JsonProperty("PreviousURL")
    private String previousURL;

    @JsonProperty("Timestamp")
    private Timestamp timestamp;

    @JsonProperty("Valute")
    private Map<String, CurrencyMap> valute;
}
