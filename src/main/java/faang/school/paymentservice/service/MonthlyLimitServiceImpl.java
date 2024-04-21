package faang.school.paymentservice.service;

import faang.school.paymentservice.dto.ExpenseCategory;
import faang.school.paymentservice.dto.MonthlyLimitDto;
import faang.school.paymentservice.entity.ExchangeRate;
import faang.school.paymentservice.entity.MonthlyLimit;
import faang.school.paymentservice.entity.Transaction;
import faang.school.paymentservice.mapper.MonthlyLimitMapper;
import faang.school.paymentservice.repository.MonthlyLimitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonthlyLimitServiceImpl implements MonthlyLimitService {
    private final MonthlyLimitRepository monthlyLimitRepository;
    private final ExchangeRateService exchangeRateService;
    private final MonthlyLimitMapper monthlyLimitMapper;

    @Override
    @Transactional
    public boolean isMonthlyLimitExceeded(Transaction transaction) {
        MonthlyLimit monthlyLimit = monthlyLimitRepository.findByAccountNumberAndExpenseCategory(transaction.getAccountFrom(), transaction.getExpenseCategory())
                .orElseGet(() -> createLimit(transaction));

        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(transaction.getCurrencyShortName(), "USD", LocalDateTime.now());
        BigDecimal sumOfTransaction = transaction.getMoneySum().multiply(exchangeRate.getCloseRate());
        BigDecimal remains = monthlyLimit.getAmount().subtract(sumOfTransaction);

        monthlyLimit.setAmount(remains);
        transaction.setMonthlyLimit(monthlyLimit);
        return (remains.compareTo(BigDecimal.ZERO) < 0); //true, если меньше нуля
    }

    @Override
    @Transactional
    public MonthlyLimitDto updateMonthlyLimit(MonthlyLimitDto monthlyLimitDto) {
        log.info("Monthly limit is going to update "+monthlyLimitDto);
        MonthlyLimit monthlyLimit = monthlyLimitRepository.findByAccountNumberAndExpenseCategory(monthlyLimitDto.getAccountNumber(), monthlyLimitDto.getExpenseCategory())
                .orElseThrow(() -> new IllegalArgumentException("Limit not found by account number and expense category"));

        monthlyLimit.setAmount(monthlyLimitDto.getAmount());
        monthlyLimit.setCurrency(monthlyLimitDto.getCurrency());
        monthlyLimit.setStartDate(LocalDate.now());
        monthlyLimit.setEndDate(LocalDate.now().plusDays(30));
        monthlyLimitRepository.save(monthlyLimit);
        log.info("Monthly limit is updated and saved to db "+monthlyLimit);
        return monthlyLimitMapper.toDto(monthlyLimit);
    }

    @Override
    @Transactional
    public List<MonthlyLimitDto> getAllLimits(String accountNumber) {
        List<MonthlyLimit> allLimits = monthlyLimitRepository.findAllByAccountNumber(accountNumber);
        return monthlyLimitMapper.toDtos(allLimits);
    }

    @Transactional
    public MonthlyLimit getLimit(String accountNumber, ExpenseCategory expenseCategory) {
        return monthlyLimitRepository.findByAccountNumberAndExpenseCategory(accountNumber, expenseCategory)
                .orElseThrow(() -> new IllegalArgumentException("Not found limit"));
    }

    @Transactional
    protected MonthlyLimit createLimit(Transaction transaction) {
        log.info("Limit is going to create");
        MonthlyLimit monthlyLimit = MonthlyLimit.builder()
                .accountNumber(transaction.getAccountFrom())
                .amount(BigDecimal.valueOf(1000))
                .expenseCategory(transaction.getExpenseCategory())
                .currency("USD")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(30))
                .build();
        log.info("Limit is created "+monthlyLimit);
        monthlyLimitRepository.save(monthlyLimit);
        transaction.setMonthlyLimit(monthlyLimit);
        return monthlyLimit;
    }
}
