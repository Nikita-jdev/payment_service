package faang.school.paymentservice.mapper;

import faang.school.paymentservice.dto.MonthlyLimitDto;
import faang.school.paymentservice.entity.MonthlyLimit;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MonthlyLimitMapper {
    MonthlyLimitDto toDto(MonthlyLimit monthlyLimit);

    MonthlyLimit toEntity(MonthlyLimitDto monthlyLimitDto);

    List<MonthlyLimitDto> toDtos(List<MonthlyLimit> monthlyLimits);
}
