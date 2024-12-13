package com.halfacode.taxi.events;

import com.halfacode.taxi.entity.Transaction;

public class TransactionCreatedEvent {

    private final Transaction transaction;

    public TransactionCreatedEvent(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}