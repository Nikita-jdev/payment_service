package faang.school.paymentservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exchange_rate")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "from_currency", nullable = false)
    private String fromCurrency;

    @NotNull
    @Column(name = "to_currency", nullable = false)
    private String toCurrency;

    @NotNull
    @Column(name = "exchange_date", nullable = false)
    private LocalDateTime exchangeDate;

    @NotNull
    @Column(name = "close_rate", nullable = false, precision = 20, scale = 10)
    private BigDecimal closeRate;

    @NotNull
    @Column(name = "previous_close_rate", nullable = false, precision = 20, scale = 10)
    private BigDecimal previousCloseRate;

    public ExchangeRate(String fromCurrency, String toCurrency, LocalDateTime exchangeDate, double exchangeRate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.exchangeDate = exchangeDate;
        this.closeRate = BigDecimal.valueOf(exchangeRate);
        this.previousCloseRate = BigDecimal.valueOf(exchangeRate);
    }
}

