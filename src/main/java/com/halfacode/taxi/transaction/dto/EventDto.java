package com.halfacode.taxi.transaction.dto;

import lombok.Data;

@Data
public class EventDto {
    private String key;
    private String eventType;
    private String fromAccount;
    private String toAccount;
    private double amount;
    private String eventId;
}