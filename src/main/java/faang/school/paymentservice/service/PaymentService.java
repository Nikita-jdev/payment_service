package faang.school.paymentservice.service;

import faang.school.paymentservice.client.OpenExchangeRatesClient;
import faang.school.paymentservice.dto.*;
import faang.school.paymentservice.service.exception.CurrencyNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private final OpenExchangeRatesClient openExchangeRatesClient;

    @Value("${services.exchange.app_id}")
    private final String app_id;

    @Value("${services.exchange.commission}")
    private final BigDecimal commission;
    private Random random;

    public PaymentResponse CurrencyExchange(PaymentRequest paymentRequest) {
        BigDecimal totalAmount = getTotalAmount(paymentRequest);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        String formattedSum = decimalFormat.format(totalAmount);
        int verificationCode = random.nextInt(1000, 10000);
        String message = String.format("Dear friend! Thank you for your purchase! " +
                        "Your payment on %s %s was accepted.",
                formattedSum, paymentRequest.currency().name());

        return new PaymentResponse(
                PaymentStatus.SUCCESS,
                verificationCode,
                paymentRequest.paymentNumber(),
                totalAmount,
                paymentRequest.currency(),
                message);
    }

    private BigDecimal getTotalAmount(PaymentRequest paymentRequest) {
        Currency currency = paymentRequest.currency();
        ExchangeRates exchangeRatesActual = openExchangeRatesClient.getExchangeRates(app_id, currency);
        BigDecimal rate = getRate(exchangeRatesActual, currency);
        if (Currency.EUR.equals(currency)) {
            log.info("The currency exchange {} was not completed due to the selected currency corresponding to the" +
                    " one being transferred", paymentRequest.paymentNumber());
            return paymentRequest.amount();
        }
        BigDecimal totalAmount = paymentRequest.amount().multiply(commission).multiply(rate);
        log.info("Successful currency exchange, payment number: {}, Currency for exchange: {} , amount received: {}"
                , paymentRequest.paymentNumber(), paymentRequest.currency().name(), totalAmount);
        return totalAmount;
    }

    private BigDecimal getRate(ExchangeRates exchangeRatesActual, Currency currency) {
        BigDecimal rate = exchangeRatesActual.getRates().get(currency.name());
        if (rate == null) {
            log.error("The specified currency: {} is not in the list", currency.name());
            throw new CurrencyNotFoundException("The specified currency: " + currency + " is not in the list");
        }
        return rate;
    }
}
