package faang.school.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostProductDto {
    private long id;
    private String name;
    private BigDecimal rub;
    private BigDecimal eur;
    private BigDecimal usd;
}
