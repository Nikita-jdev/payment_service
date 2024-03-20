package faang.school.paymentservice.controller;

import faang.school.paymentservice.context.UserContext;
import faang.school.paymentservice.dto.DmsDto;
import faang.school.paymentservice.service.dms.DmsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class DmsController {
    private final DmsService dmsService;
    private final UserContext userContext;

    @PostMapping
    public DmsDto authorizationRequest(@RequestBody @Valid DmsDto dmsDto) {
        return dmsService.authorizationRequest(dmsDto, userContext.getUserId());
    }

    @PutMapping("/cancel/{transactionId}")
    public DmsDto cancelMessage(@PathVariable long transactionId) {
        return dmsService.cancelRequest(transactionId, userContext.getUserId());
    }

    @PutMapping("/force/{transactionId}")
    public DmsDto forced(@PathVariable long transactionId) {
        return dmsService.forcedRequest(transactionId, userContext.getUserId());
    }
}
