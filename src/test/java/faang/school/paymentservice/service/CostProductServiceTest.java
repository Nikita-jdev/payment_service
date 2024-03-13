package faang.school.paymentservice.service;

import faang.school.paymentservice.dto.CostProductDto;
import faang.school.paymentservice.entity.CostProduct;
import faang.school.paymentservice.mapper.CostProductMapperImpl;
import faang.school.paymentservice.products.Premium;
import faang.school.paymentservice.products.ProductsBuilder;
import faang.school.paymentservice.repository.CostProductRedisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CostProductServiceTest {
    @Mock
    private CostProductRedisRepository costProductRedisRepository;
    @Spy
    private CostProductMapperImpl costProductMapper;
    @Mock
    private List<ProductsBuilder> productBuilders;

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

    @Test
    void testSaveCostProduct() {
        Premium premium = mock(Premium.class);
        CostProduct costProduct = CostProduct.builder().name("Product").build();
        List<ProductsBuilder> productBuilders1 = Collections.singletonList(premium);
        when(premium.build()).thenReturn(costProduct);
        when(productBuilders.stream()).thenReturn(productBuilders1.stream());
        when(costProductRedisRepository.existsByName(anyString())).thenReturn(false);
        ArgumentCaptor<CostProduct> captor = ArgumentCaptor.forClass(CostProduct.class);
        costProductService.saveCostProduct();
        verify(costProductRedisRepository, times(1)).save(captor.capture());
        CostProduct capturedProduct = captor.getValue();
        assertEquals("Product", capturedProduct.getName());
    }
}