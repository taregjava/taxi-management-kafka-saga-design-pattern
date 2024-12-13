package com.halfacode.taxi.transaction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Account {
    @Id
    private String accountId;
    private String name;
    private double balance;

}