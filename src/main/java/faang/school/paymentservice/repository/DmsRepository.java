package faang.school.paymentservice.repository;

import faang.school.paymentservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DmsRepository extends JpaRepository<Transaction, Long> {
    @Query(nativeQuery = true, value = """
            SELECT * FROM transaction WHERE scheduled_at < now() AND request_status::text = 'PENDING' ORDER BY scheduled_at
            """)
    List<Transaction> transactionsToConfirm();
}
