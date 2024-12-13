package com.halfacode.taxi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "processed_messages")
@Data
public class ProcessedMessage {

    @Id
    private String messageId; // Use transactionId or a unique identifier for the message

    private LocalDateTime processedAt;

    public ProcessedMessage() {
        this.processedAt = LocalDateTime.now();
    }
}
