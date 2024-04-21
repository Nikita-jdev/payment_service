package faang.school.paymentservice.repository;

import faang.school.paymentservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("""
            SELECT t FROM Transaction t
            WHERE t.accountFrom = :accountNumber
            AND t.limitExceeded = true
            """)
    List<Transaction> findLimitExceededTransactions(String accountNumber);

}
