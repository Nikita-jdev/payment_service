package faang.school.paymentservice.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import faang.school.paymentservice.dto.ExpenseCategory;
import faang.school.paymentservice.dto.TransactionDto;
import faang.school.paymentservice.entity.MonthlyLimit;
import faang.school.paymentservice.entity.Transaction;
import faang.school.paymentservice.mapper.TransactionMapper;
import faang.school.paymentservice.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @InjectMocks
    private TransactionServiceImpl transactionService;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private MonthlyLimitServiceImpl monthlyLimitService;
    @Mock
    private TransactionMapper transactionMapper;

    Transaction transaction;
    MonthlyLimit monthlyLimit;
    TransactionDto transactionDto;
    @BeforeEach
    void setUp() {
        monthlyLimit = MonthlyLimit.builder()
                .accountNumber("123123123")
                .expenseCategory(ExpenseCategory.valueOf("SERVICE"))
                .amount(BigDecimal.valueOf(1000))
                .currency("USD")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(30))
                .build();

        transaction = Transaction.builder()
                .accountFrom("123123123")
                .accountTo("234234234")
                .currencyShortName("USD")
                .datetime(ZonedDateTime.now())
                .expenseCategory(ExpenseCategory.valueOf("SERVICE"))
                .moneySum(BigDecimal.ONE)
                .limitExceeded(false)
                .monthlyLimit(monthlyLimit)
                .build();
        transactionDto = TransactionDto.builder()
                .accountFrom("123123123")
                .accountTo("234234234")
                .currencyShortName("USD")
                .datetime(ZonedDateTime.now())
                .expenseCategory(ExpenseCategory.valueOf("SERVICE"))
                .moneySum(BigDecimal.ONE)
                .build();
    }


    @Test
    void testLimitExceededInvalid() {
        when(transactionMapper.toEntity(transactionDto)).thenReturn(transaction);
        when(monthlyLimitService.isMonthlyLimitExceeded(transaction)).thenReturn(true);

        transactionService.saveTransaction(transactionDto);
        assertTrue(transaction.isLimitExceeded());
    }

    @Test
    void testIsLimitExceeded() {
        when(transactionMapper.toEntity(transactionDto)).thenReturn(transaction);
        when(monthlyLimitService.isMonthlyLimitExceeded(transaction)).thenReturn(false);

        transactionService.saveTransaction(transactionDto);
        assertFalse(transaction.isLimitExceeded());
    }

    @Test
    void testIsTransactionSaved() {
        when(transactionMapper.toEntity(transactionDto)).thenReturn(transaction);
        when(monthlyLimitService.isMonthlyLimitExceeded(transaction)).thenReturn(true);

        transactionService.saveTransaction(transactionDto);
        verify(transactionRepository, times(1)).save(transaction);
    }
}
