package faang.school.paymentservice.service;

import faang.school.paymentservice.dto.TransactionDto;
import faang.school.paymentservice.entity.ExchangeRate;
import faang.school.paymentservice.entity.Transaction;
import faang.school.paymentservice.mapper.TransactionMapper;
import faang.school.paymentservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final MonthlyLimitService monthlyLimitService;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        log.info("Transaction is going to save");
        Transaction transaction = transactionMapper.toEntity(transactionDto);
        if (monthlyLimitService.isMonthlyLimitExceeded(transaction)) {
            transaction.setLimitExceeded(true);
        }

        transactionRepository.save(transaction);
        log.info("Transaction is saved to DB: " + transaction);
        return transactionMapper.toDto(transaction);
    }

    @Override
    @Transactional
    public List<TransactionDto> getLimitExceededTransactions(String accountNumber) {
        return transactionRepository.findLimitExceededTransactions(accountNumber).stream()
                .map(transactionMapper::toDto).toList();
    }
}
