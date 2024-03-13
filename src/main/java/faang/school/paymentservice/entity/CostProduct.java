package faang.school.paymentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("costProducts")
public class CostProduct {
    @Id
    private long id;
    @Indexed
    private String name;
    private BigDecimal usd;
    private BigDecimal eur;
    private BigDecimal rub;
}
