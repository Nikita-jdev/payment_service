package faang.school.paymentservice.service;

import faang.school.paymentservice.dto.TransactionDto;
import faang.school.paymentservice.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    TransactionDto saveTransaction(TransactionDto transactionDto);

    List<TransactionDto> getLimitExceededTransactions(String accountNumber);
}
