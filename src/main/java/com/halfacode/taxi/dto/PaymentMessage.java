package com.halfacode.taxi.dto;

import lombok.Data;

@Data
public class PaymentMessage {

    private String transactionId;

    private String userId;

    private double selectedAmount;

    private double paidAmount;

    public PaymentMessage() {}

    public PaymentMessage(String transactionId, String userId, double selectedAmount, double paidAmount) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.selectedAmount = selectedAmount;
        this.paidAmount = paidAmount;
    }

    // Getters and Setters
}
