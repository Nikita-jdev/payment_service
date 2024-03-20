package faang.school.paymentservice.dto;

import faang.school.paymentservice.enums.RequestStatus;
import faang.school.paymentservice.enums.RequestType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DmsDto {
    private Long id;

    @NotBlank
    private String senderAccountNumber;

    @NotBlank
    private String receiverAccountNumber;

    @NotNull
    private BigDecimal moneyAmount;

    @NotBlank
    private String moneyCurrency;

    @NotNull
    private UUID idempotencyKey;

    private RequestStatus requestStatus;

    private RequestType requestType;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long userId;
}
