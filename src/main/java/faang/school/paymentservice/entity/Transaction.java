package faang.school.paymentservice.entity;


import java.math.BigDecimal;
import java.time.ZonedDateTime;



@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_from", nullable = false)
    private String accountFrom;

    @Column(name = "account_to", nullable = false)
    private String accountTo;

    @Column(name = "currency_shortname", nullable = false)
    private String currencyShortname;

    @Column(name = "money_sum", nullable = false)
    private BigDecimal moneySum;

    @Column(name = "expense_category", nullable = false)
    private String expenseCategory;

    @Column(name = "datetime", nullable = false)
    private ZonedDateTime datetime;

    @Column(name = "limit_exceeded", nullable = false)
    private boolean limitExceeded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthly_limit_id", nullable = false)
    private MonthlyLimit monthlyLimit;

}

