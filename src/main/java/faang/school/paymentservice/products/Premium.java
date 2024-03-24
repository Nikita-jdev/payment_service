package faang.school.paymentservice.products;

import faang.school.paymentservice.entity.CostProduct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;
@Component
public class Premium implements ProductsBuilder{
    @Value("${payment.product.premium.name}")
    private String productName;
    @Value(("${payment.product.premium.price_usd}"))
    private BigDecimal productPrice;
    @Override
    public CostProduct build() {
        return CostProduct.builder()
                .id(Math.abs(UUID.randomUUID().getMostSignificantBits()))
                .name(productName)
                .usd(productPrice)
                .build();
    }
}
