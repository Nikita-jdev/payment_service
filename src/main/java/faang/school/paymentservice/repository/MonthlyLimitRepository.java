package faang.school.paymentservice.repository;

import faang.school.paymentservice.dto.ExpenseCategory;
import faang.school.paymentservice.entity.MonthlyLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonthlyLimitRepository extends JpaRepository<MonthlyLimit, Long> {
    Optional<MonthlyLimit> findByAccountNumberAndExpenseCategory(String accountNumber, ExpenseCategory expenseCategory);

    List<MonthlyLimit> findAllByAccountNumber(String accountNumber);
}
