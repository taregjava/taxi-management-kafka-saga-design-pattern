package com.halfacode.taxi.transaction.service;

import com.halfacode.taxi.transaction.dto.EventDto;
import com.halfacode.taxi.transaction.dto.TransferRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    @Autowired
    private KafkaTemplate<String, EventDto> kafkaTemplate;

    public void processTransfer(TransferRequestDto transferRequest) {
        String eventId = generateEventId();

        // Create debit event
        EventDto debitEvent = new EventDto();
        debitEvent.setKey(transferRequest.getFromAccount());
        debitEvent.setEventType("debit");
        debitEvent.setFromAccount(transferRequest.getFromAccount());
        debitEvent.setToAccount(transferRequest.getToAccount());
        debitEvent.setAmount(transferRequest.getAmount());
        debitEvent.setEventId(eventId);

        // Send to Kafka
        kafkaTemplate.send("account-transactions", debitEvent.getKey(), debitEvent);

        // Create credit event
        EventDto creditEvent = new EventDto();
        creditEvent.setKey(transferRequest.getToAccount());
        creditEvent.setEventType("credit");
        creditEvent.setFromAccount(transferRequest.getFromAccount());
        creditEvent.setToAccount(transferRequest.getToAccount());
        creditEvent.setAmount(transferRequest.getAmount());
        creditEvent.setEventId(eventId);

        // Send to Kafka
        kafkaTemplate.send("account-transactions", creditEvent.getKey(), creditEvent);
    }

    private String generateEventId() {
        return java.util.UUID.randomUUID().toString();
    }
}
