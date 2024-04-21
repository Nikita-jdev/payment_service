package faang.school.paymentservice.service;

import faang.school.paymentservice.dto.ExpenseCategory;
import faang.school.paymentservice.dto.MonthlyLimitDto;
import faang.school.paymentservice.entity.MonthlyLimit;
import faang.school.paymentservice.entity.Transaction;

import java.util.List;

public interface MonthlyLimitService {
    boolean isMonthlyLimitExceeded(Transaction transaction);

    MonthlyLimitDto updateMonthlyLimit(MonthlyLimitDto monthlyLimitDto);

    List<MonthlyLimitDto> getAllLimits(String accountNumber);

    MonthlyLimit getLimit(String accountNumber, ExpenseCategory expenseCategory);
}
