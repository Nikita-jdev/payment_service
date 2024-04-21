package faang.school.paymentservice.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import faang.school.paymentservice.dto.ExpenseCategory;
import faang.school.paymentservice.dto.MonthlyLimitDto;
import faang.school.paymentservice.dto.TransactionDto;
import faang.school.paymentservice.entity.ExchangeRate;
import faang.school.paymentservice.entity.MonthlyLimit;
import faang.school.paymentservice.entity.Transaction;
import faang.school.paymentservice.service.MonthlyLimitService;
import faang.school.paymentservice.service.MonthlyLimitServiceImpl;
import faang.school.paymentservice.service.TransactionService;
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
public class MonthlyLimitControllerTest {
    @InjectMocks
    private MonthlyLimitController monthlyLimitController;
    @Mock
    private MonthlyLimitService monthlyLimitService;

    MonthlyLimitDto monthlyLimitDto;

    @BeforeEach
    void setUp() {
        monthlyLimitDto = MonthlyLimitDto.builder()
                .accountNumber("123123123")
                .expenseCategory(ExpenseCategory.valueOf("SERVICE"))
                .amount(BigDecimal.valueOf(1000))
                .currency("USD")
                .build();
    }

    @Test
    void testMonthlyLimitIsUpdated() {
        monthlyLimitController.updateMonthlyLimit(monthlyLimitDto);
        verify(monthlyLimitService, times(1)).updateMonthlyLimit(monthlyLimitDto);
    }

    @Test
    void testGetAllLimits() {
        monthlyLimitController.getAllLimits("123123123");
        verify(monthlyLimitService, times(1)).getAllLimits("123123123");
    }
}
