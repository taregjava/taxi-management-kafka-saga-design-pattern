package com.halfacode.taxi.events;

import com.halfacode.taxi.dto.PaymentMessage;

public class PaymentReceivedEvent {

    private final PaymentMessage paymentMessage;

    public PaymentReceivedEvent(PaymentMessage paymentMessage) {
        this.paymentMessage = paymentMessage;
    }

    public PaymentMessage getPaymentMessage() {
        return paymentMessage;
    }
}
