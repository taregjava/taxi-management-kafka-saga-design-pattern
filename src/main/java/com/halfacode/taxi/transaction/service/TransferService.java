package com.halfacode.taxi.transaction.service;

import com.halfacode.taxi.transaction.dto.EventDto;
import com.halfacode.taxi.transaction.dto.TransferRequestDto;
import com.halfacode.taxi.transaction.entity.Account;
import com.halfacode.taxi.transaction.entity.TransactionEvent;
import com.halfacode.taxi.transaction.repository.AccountRepository;
import com.halfacode.taxi.transaction.repository.TransactionEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private TransactionEventRepository eventRepository;
    @Transactional
    public void processTransfer(TransferRequestDto transferRequest) {
        // Pre-check: Validate balance in the source account
        Account sourceAccount = accountRepository.findById(transferRequest.getFromAccount())
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        if (sourceAccount.getBalance() < transferRequest.getAmount()) {
            throw new RuntimeException("Insufficient balance in account: " + transferRequest.getFromAccount());
        }

        String eventId = generateEventId();

        kafkaTemplate.executeInTransaction(operations -> {
            // Create debit event
            EventDto debitEvent = new EventDto();
            debitEvent.setKey(transferRequest.getFromAccount());
            debitEvent.setEventType("debit");
            debitEvent.setFromAccount(transferRequest.getFromAccount());
            debitEvent.setToAccount(transferRequest.getToAccount());
            debitEvent.setAmount(transferRequest.getAmount());
            debitEvent.setEventId(eventId);

            // Create and save debit transaction event
            TransactionEvent debitTransactionEvent = new TransactionEvent();
            debitTransactionEvent.setEventId(eventId);
            debitTransactionEvent.setEventType("debit");
            debitTransactionEvent.setAccountId(transferRequest.getFromAccount());
            debitTransactionEvent.setAmount(transferRequest.getAmount());
            debitTransactionEvent.setRelatedAccountId(transferRequest.getToAccount());
            eventRepository.save(debitTransactionEvent);

            // Create credit event
            EventDto creditEvent = new EventDto();
            creditEvent.setKey(transferRequest.getToAccount());
            creditEvent.setEventType("credit");
            creditEvent.setFromAccount(transferRequest.getFromAccount());
            creditEvent.setToAccount(transferRequest.getToAccount());
            creditEvent.setAmount(transferRequest.getAmount());
            creditEvent.setEventId(eventId);

            // Create and save credit transaction event
            TransactionEvent creditTransactionEvent = new TransactionEvent();
            creditTransactionEvent.setEventId(eventId);
            creditTransactionEvent.setEventType("credit");
            creditTransactionEvent.setAccountId(transferRequest.getToAccount());
            creditTransactionEvent.setAmount(transferRequest.getAmount());
            creditTransactionEvent.setRelatedAccountId(transferRequest.getFromAccount());
            eventRepository.save(creditTransactionEvent);

            // Send both events to Kafka
            operations.send("account-transactions", debitEvent.getKey(), debitEvent.toString());
            operations.send("account-transactions", creditEvent.getKey(), creditEvent.toString());

            return null;
        });
    }


    private String generateEventId() {
        return java.util.UUID.randomUUID().toString();
    }
}