package com.halfacode.taxi.transaction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TransactionEvent {
    @Id
    private String eventId;
    private String eventType; // "debit" or "credit"
    private String accountId;
    private double amount;
    private String relatedAccountId;
}