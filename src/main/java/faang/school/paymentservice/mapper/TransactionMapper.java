package faang.school.paymentservice.mapper;

import faang.school.paymentservice.dto.TransactionDto;
import faang.school.paymentservice.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {
    Transaction toEntity(TransactionDto transactionDto);

    TransactionDto toDto(Transaction transaction);
}
