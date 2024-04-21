package faang.school.paymentservice.controller;

import faang.school.paymentservice.dto.MonthlyLimitDto;
import faang.school.paymentservice.service.MonthlyLimitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/limits")
public class MonthlyLimitController {
    private final MonthlyLimitService monthlyLimitService;

    @PutMapping
    public MonthlyLimitDto updateMonthlyLimit(@RequestBody @Valid MonthlyLimitDto monthlyLimitDto) {
        return monthlyLimitService.updateMonthlyLimit(monthlyLimitDto);
    }

    @GetMapping("/{accountNumber}")
    public List<MonthlyLimitDto> getAllLimits(@PathVariable String accountNumber) {
        return monthlyLimitService.getAllLimits(accountNumber);
    }
}
