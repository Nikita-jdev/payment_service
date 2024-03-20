package faang.school.paymentservice.mapper;

import faang.school.paymentservice.dto.DmsDto;
import faang.school.paymentservice.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DmsMapper {

    Transaction toEntity(DmsDto dmsDto);

    DmsDto toDto(Transaction transaction);
}
