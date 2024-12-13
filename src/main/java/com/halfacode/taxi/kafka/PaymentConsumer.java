package com.halfacode.taxi.kafka;

import com.halfacode.taxi.dto.PaymentMessage;
import com.halfacode.taxi.entity.Transaction;
import com.halfacode.taxi.events.PaymentReceivedEvent;
import com.halfacode.taxi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
@Service
public class PaymentConsumer {

    @Autowired
    private TransactionService transactionService;

    @KafkaListener(topics = "payment_transactions", groupId = "ticket-atm-group")
    public void consume(PaymentMessage message) {
        System.out.println("Consumed message: " + message);

        // Idempotency check
        if (transactionService.isMessageProcessed(message.getTransactionId())) {
            System.out.println("Duplicate message. Skipping: " + message.getTransactionId());
            return;
        }

        // Process message
        synchronized (this) {
            Transaction transaction = transactionService.findByTransactionId(message.getTransactionId());
            if (transaction == null) {
                System.out.println("Transaction not found: " + message.getTransactionId());
                return;
            }

            // Update transaction
            double newPaidAmount = transaction.getPaidAmount() + message.getPaidAmount();
            transaction.setPaidAmount(newPaidAmount);

            // Update status
            if (newPaidAmount > transaction.getSelectedAmount()) {
                transaction.setStatus("OVERPAID");
            } else if (newPaidAmount == transaction.getSelectedAmount()) {
                transaction.setStatus("COMPLETED");
            } else {
                transaction.setStatus("PENDING");
            }

            // Save updated transaction
            transactionService.saveTransaction(transaction);

            // Mark message as processed
            transactionService.markMessageAsProcessed(message.getTransactionId());

            System.out.println("Updated transaction: " + transaction);
        }
    }
}
