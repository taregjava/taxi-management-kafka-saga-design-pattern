package com.halfacode.taxi.listener;

import com.halfacode.taxi.entity.Transaction;
import com.halfacode.taxi.events.PaymentReceivedEvent;
import com.halfacode.taxi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {

    @Autowired
    private TransactionService transactionService;

    @EventListener
    public void handlePaymentReceived(PaymentReceivedEvent event) {
        var paymentMessage = event.getPaymentMessage();
        Transaction transaction = transactionService.findByTransactionId(paymentMessage.getTransactionId());

        if (transaction != null) {
            double newPaidAmount = transaction.getPaidAmount() + paymentMessage.getPaidAmount();
            transaction.setPaidAmount(newPaidAmount);

            if (newPaidAmount >= transaction.getSelectedAmount()) {
                transaction.setStatus("COMPLETED");
            } else {
                transaction.setStatus("PENDING");
            }

            transactionService.saveTransaction(transaction);
        }
    }
}