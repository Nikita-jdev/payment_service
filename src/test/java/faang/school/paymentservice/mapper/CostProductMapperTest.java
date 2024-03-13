package faang.school.paymentservice.mapper;

import faang.school.paymentservice.dto.CostProductDto;
import faang.school.paymentservice.entity.CostProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

class CostProductMapperTest {
    private final CostProductMapper costProductMapper= Mappers.getMapper(CostProductMapper.class);
    @Test
    void testToDto() {
        CostProduct costProduct = CostProduct.builder().build();
        CostProductDto costProductDto = CostProductDto.builder().build();
        assertEquals(costProductDto,costProductMapper.toDto(costProduct));
    }
}