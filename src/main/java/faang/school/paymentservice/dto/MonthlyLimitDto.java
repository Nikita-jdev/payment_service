package faang.school.paymentservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyLimitDto {
    @NotNull
    private String accountNumber;

    @NotNull
    private ExpenseCategory expenseCategory;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String currency;
}
