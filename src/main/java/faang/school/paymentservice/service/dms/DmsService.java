package faang.school.paymentservice.service.dms;

import faang.school.paymentservice.dto.DmsDto;
import faang.school.paymentservice.dto.DmsEvent;
import faang.school.paymentservice.dto.Money;
import faang.school.paymentservice.entity.Transaction;
import faang.school.paymentservice.enums.RequestStatus;
import faang.school.paymentservice.enums.RequestType;
import faang.school.paymentservice.mapper.DmsMapper;
import faang.school.paymentservice.publisher.DmsEventPublisher;
import faang.school.paymentservice.repository.DmsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DmsService {
    private final DmsMapper dmsMapper;
    private final DmsRepository dmsRepository;
    private final DmsEventPublisher dmsEventPublisher;

    @Value("${spring.scheduler.clearScheduledAt.hours}")
    private int clearScheduledAt;


    @Transactional
    public DmsDto authorizationRequest(DmsDto dmsDto, Long userId) {
        dmsDto.setUserId(userId);
        Transaction transaction = dmsMapper.toEntity(dmsDto);
        transaction.setRequestStatus(RequestStatus.PENDING);
        transaction.setRequestType(RequestType.AUTHORIZATION);
        transaction.setClearScheduledAt(LocalDateTime.now().plusHours(clearScheduledAt));

        return saveAndPublishTransaction(transaction, userId);
    }

    @Transactional
    public DmsDto cancelRequest(Long transactionId, Long userId) {
        Transaction transaction = getTransactionById(transactionId);
        transaction.setRequestType(RequestType.CANCEL);
        transaction.setRequestStatus(RequestStatus.SUCCESS);
        transaction.setUserId(userId);

        return saveAndPublishTransaction(transaction, userId);
    }

    @Transactional
    public DmsDto forcedRequest(Long transactionId, Long userId) {
        Transaction transaction = getTransactionById(transactionId);
        transaction.setRequestType(RequestType.CLEARING);
        transaction.setRequestStatus(RequestStatus.SUCCESS);
        transaction.setUserId(userId);

        return saveAndPublishTransaction(transaction, userId);
    }

    @Transactional
    @Scheduled(cron = "${spring.scheduler.confirmTransactions.cronExpression}")
    public void confirmTransactions() {
        List<Transaction> transactionsToConfirm = dmsRepository.transactionsToConfirm();

        if (transactionsToConfirm.isEmpty()) {
            return;
        }

        transactionsToConfirm.forEach(transaction -> {
                    transaction.setRequestType(RequestType.CLEARING);
                    transaction.setRequestStatus(RequestStatus.SUCCESS);
                    saveAndPublishTransaction(transaction, transaction.getUserId());
                });
    }

    @Transactional(readOnly = true)
    public Transaction getTransactionById(Long transactionId) {
        return dmsRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found by id "+transactionId));
    }

    private DmsEvent buildDmsEvent(Transaction transaction, Long userId) {
        Money money = new Money(transaction.getMoneyAmount(), transaction.getMoneyCurrency());
        return DmsEvent.builder()
                .money(money)
                .requestType(transaction.getRequestType())
                .requestStatus(transaction.getRequestStatus())
                .receiverAccountNumber(transaction.getReceiverAccountNumber())
                .senderAccountNumber(transaction.getSenderAccountNumber())
                .senderId(userId)
                .build();
    }

    @Transactional
    private DmsDto saveAndPublishTransaction(Transaction transaction, Long userId) {
        dmsRepository.save(transaction);

        DmsEvent dmsEvent = buildDmsEvent(transaction, userId);
        dmsEventPublisher.publish(dmsEvent);

        return dmsMapper.toDto(transaction);
    }
}
