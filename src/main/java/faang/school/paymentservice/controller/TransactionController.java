package faang.school.paymentservice.controller;

import faang.school.paymentservice.dto.TransactionDto;
import faang.school.paymentservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public TransactionDto saveTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        return transactionService.saveTransaction(transactionDto);
    }

    @GetMapping("/{accountNumber}/exceeded")
    public List<TransactionDto> getLimitExceededTransactions(@PathVariable String accountNumber) {
        return transactionService.getLimitExceededTransactions(accountNumber);
    }

}
