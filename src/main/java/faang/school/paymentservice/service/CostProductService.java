package faang.school.paymentservice.service;

import faang.school.paymentservice.dto.CostProductDto;
import faang.school.paymentservice.entity.CostProduct;
import faang.school.paymentservice.mapper.CostProductMapper;
import faang.school.paymentservice.products.ProductsBuilder;
import faang.school.paymentservice.repository.CostProductRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CostProductService {
    private final CostProductRedisRepository costProductRedisRepository;
    private final CostProductMapper costProductMapper;
    private final List<ProductsBuilder> productBuilders;

    public CostProductDto getCostProduct(long id) {
        CostProduct costProduct = costProductRedisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cost product not found"));
        log.info("Cost product found: " + costProduct);
        return costProductMapper.toDto(costProduct);
    }

    public void saveCostProduct() {
        for (ProductsBuilder productBuilder : productBuilders) {
            boolean exists = costProductRedisRepository.existsByName(productBuilder.build().getName());
            if (!exists) {
                costProductRedisRepository.save(productBuilder.build());
                log.info("Cost product saved: " + productBuilder.build());
            } else {
                log.info("Cost product already exists: " + productBuilder.build());
            }
        }
    }
}
