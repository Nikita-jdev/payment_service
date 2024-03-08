package faang.school.paymentservice.controller;

import faang.school.paymentservice.dto.Currency;
import faang.school.paymentservice.dto.PaymentRequest;
import faang.school.paymentservice.dto.PaymentResponse;
import faang.school.paymentservice.dto.PaymentStatus;
import faang.school.paymentservice.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentController {
    private final ExchangeService exchangeService;
    @Value("${payment.fee}")
    private double paymentFee;
    @Value("${payment.currency}")
    private Currency serviceCurrency;
    @PostMapping("/payment")
    public ResponseEntity<PaymentResponse> sendPayment(@RequestBody @Validated PaymentRequest dto) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedSum = decimalFormat.format(dto.amount());
        int verificationCode = new Random().nextInt(1000, 10000);
        String message = String.format("Dear friend! Thank you for your purchase! " +
                        "Your payment on %s %s was accepted.",
                formattedSum, dto.currency().name());

        BigDecimal amount = calculatePayment(dto.amount(), dto.currency());

        return ResponseEntity.ok(new PaymentResponse(
                PaymentStatus.SUCCESS,
                verificationCode,
                dto.paymentNumber(),
                amount,
                dto.currency(),
                message)
        );
    }

    private BigDecimal calculatePayment (BigDecimal amount, Currency userCurrency) {
        BigDecimal exchangeRate = exchangeService.exchange(userCurrency, serviceCurrency);
        return amount
                .multiply(exchangeRate)
                .multiply(BigDecimal.valueOf(paymentFee));
    }
}
