package com.halfacode.taxi.transaction.controller;

import com.halfacode.taxi.transaction.dto.TransferRequestDto;
import com.halfacode.taxi.transaction.dto.TransferResponseDto;
import com.halfacode.taxi.transaction.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping
    public ResponseEntity<TransferResponseDto> transferFunds(@RequestBody TransferRequestDto transferRequest) {
        try {
            transferService.processTransfer(transferRequest);
            TransferResponseDto response = new TransferResponseDto("SUCCESS", "Transfer completed successfully.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            TransferResponseDto response = new TransferResponseDto("FAILURE", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            TransferResponseDto response = new TransferResponseDto("FAILURE", "An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
