package faang.school.paymentservice.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import faang.school.paymentservice.dto.ExpenseCategory;
import faang.school.paymentservice.dto.MonthlyLimitDto;
import faang.school.paymentservice.entity.ExchangeRate;
import faang.school.paymentservice.entity.MonthlyLimit;
import faang.school.paymentservice.entity.Transaction;
import faang.school.paymentservice.mapper.MonthlyLimitMapper;
import faang.school.paymentservice.repository.MonthlyLimitRepository;
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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MonthlyLimitServiceTest {
    @InjectMocks
    private MonthlyLimitServiceImpl monthlyLimitService;
    @Mock
    private MonthlyLimitRepository monthlyLimitRepository;
    @Mock
    private ExchangeRateService exchangeRateService;
    @Mock
    private MonthlyLimitMapper monthlyLimitMapper;

    Transaction transaction;
    MonthlyLimit monthlyLimit;
    MonthlyLimitDto monthlyLimitDto;
    ExchangeRate exchangeRate;

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

        monthlyLimitDto = MonthlyLimitDto.builder()
                .accountNumber("123123123")
                .expenseCategory(ExpenseCategory.valueOf("SERVICE"))
                .amount(BigDecimal.valueOf(1000))
                .currency("USD")
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
        exchangeRate = ExchangeRate.builder()
                .fromCurrency("USD")
                .toCurrency("USD")
                .closeRate(BigDecimal.valueOf(1))
                .previousCloseRate(BigDecimal.ONE)
                .exchangeDate(LocalDateTime.now())
                .build();
    }


    @Test
    void testCreateLimit() {
        monthlyLimitService.createLimit(transaction);
        verify(monthlyLimitRepository, times(1)).save(monthlyLimit);
    }

    @Test
    void testLimitIsInvalid() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> monthlyLimitService.getLimit("123123123", ExpenseCategory.valueOf("SERVICE")));
        assertEquals("Not found limit", illegalArgumentException.getMessage());
    }

    @Test
    void testUpdateLimit() {
        when(monthlyLimitRepository.findByAccountNumberAndExpenseCategory(monthlyLimit.getAccountNumber(), monthlyLimit.getExpenseCategory()))
                .thenReturn(Optional.of(monthlyLimit));
        monthlyLimitService.updateMonthlyLimit(monthlyLimitDto);
        verify(monthlyLimitRepository, times(1)).save(monthlyLimit);
    }
}
