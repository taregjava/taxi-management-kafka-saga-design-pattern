package com.halfacode.taxi.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferResponseDto {
    private String status;
    private String message;

    // Constructors, getters, and setters
}
