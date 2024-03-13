package faang.school.paymentservice.mapper;

import faang.school.paymentservice.dto.CostProductDto;
import faang.school.paymentservice.entity.CostProduct;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface CostProductMapper {

    CostProductDto toDto(CostProduct costProduct);
}
