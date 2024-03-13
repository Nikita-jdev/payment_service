package faang.school.paymentservice.sheduler;

import faang.school.paymentservice.service.CurrencyConverterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledCurrencyConvert {
    private final CurrencyConverterService currencyConverterService;

    @Scheduled(cron = "${scheduler.convert.time}")
    public void convert() {
        currencyConverterService.convert();
    }
}
