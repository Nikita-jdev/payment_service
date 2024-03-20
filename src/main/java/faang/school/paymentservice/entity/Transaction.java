package faang.school.paymentservice.entity;

import faang.school.paymentservice.dto.Currency;
import faang.school.paymentservice.enums.RequestStatus;
import faang.school.paymentservice.enums.RequestType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Version;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idempotency_key", unique = true, nullable = false)
    private UUID idempotencyKey;

    @Column(name = "sender_account_number", unique = true, nullable = false)
    private String senderAccountNumber;

    @Column(name = "receiver_account_number", unique = true, nullable = false)
    private String receiverAccountNumber;

    @Column(name = "money_amount", nullable = false)
    private BigDecimal moneyAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "money_currency", nullable = false)
    private Currency moneyCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status", nullable = false)
    private RequestStatus requestStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type", nullable = false)
    private RequestType requestType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "clear_scheduled_at", nullable = false)
    private LocalDateTime clearScheduledAt;

    @Version
    @Column(name = "version")
    private long version;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
