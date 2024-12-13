package com.halfacode.taxi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;

    private String userId;

    private double selectedAmount;

    private double paidAmount;

    private String status; // "PENDING", "COMPLETED", "CANCELLED"

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Version
    private Integer version;
    public Transaction() {}

    public Transaction(String transactionId, String userId, double selectedAmount, double paidAmount, String status) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.selectedAmount = selectedAmount;
        this.paidAmount = paidAmount;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
}
