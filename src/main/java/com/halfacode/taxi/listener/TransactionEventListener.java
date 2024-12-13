package com.halfacode.taxi.listener;

import com.halfacode.taxi.dto.PaymentMessage;
import com.halfacode.taxi.events.TransactionCreatedEvent;
import com.halfacode.taxi.kafka.PaymentProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventListener {

    @Autowired
    private PaymentProducer paymentProducer;

    @EventListener
    public void handleTransactionCreated(TransactionCreatedEvent event) {
        PaymentMessage message = new PaymentMessage(
                event.getTransaction().getTransactionId(),
                event.getTransaction().getUserId(),
                event.getTransaction().getSelectedAmount(),
                event.getTransaction().getPaidAmount()
        );

        paymentProducer.sendPayment(message);
    }
}
