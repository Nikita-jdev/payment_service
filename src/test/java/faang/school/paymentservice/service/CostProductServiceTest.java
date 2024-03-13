package faang.school.paymentservice.service;

import faang.school.paymentservice.dto.CostProductDto;
import faang.school.paymentservice.entity.CostProduct;
import faang.school.paymentservice.mapper.CostProductMapperImpl;
import faang.school.paymentservice.repository.CostProductRedisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CostProductServiceTest {
    @Mock
    private CostProductRedisRepository costProductRedisRepository;
    @Spy
    private CostProductMapperImpl costProductMapper;
    @InjectMocks
    private CostProductService costProductService;

    @Test
    void testGetCostProduct() {
        long costProductId = 1L;
        CostProduct costProduct = CostProduct.builder().build();
         when(costProductRedisRepository.findById(costProductId)).thenReturn(Optional.of(costProduct));
        CostProductDto costProductDto = CostProductDto.builder().build();
        assertEquals(costProductDto, costProductService.getCostProduct(costProductId));
    }

    @Test
    void testGetCostProductNotFound() {
        long costProductId = 1L;
        when(costProductRedisRepository.findById(costProductId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> costProductService.getCostProduct(costProductId));
        assertEquals("Cost product not found", exception.getMessage());
    }
}