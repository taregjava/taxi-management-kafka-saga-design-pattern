package com.halfacode.taxi.controller;

import com.halfacode.taxi.dto.PaymentMessage;
import com.halfacode.taxi.entity.Transaction;
import com.halfacode.taxi.events.TransactionCreatedEvent;
import com.halfacode.taxi.kafka.PaymentProducer;
import com.halfacode.taxi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PaymentProducer paymentProducer;

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        transaction.setStatus("PENDING");
        Transaction savedTransaction = transactionService.saveTransaction(transaction);

        // Send to Kafka
        PaymentMessage message = new PaymentMessage(
                savedTransaction.getTransactionId(),
                savedTransaction.getUserId(),
                savedTransaction.getSelectedAmount(),
                savedTransaction.getPaidAmount()
        );
        paymentProducer.sendPayment(message);

        return ResponseEntity.ok(savedTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
/*    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id).map(transaction -> {
            Map<String, Object> response = new HashMap<>();
            response.put("transaction", transaction);
            if (transaction.getPaidAmount() > transaction.getSelectedAmount()) {
                response.put("overpayment", transaction.getPaidAmount() - transaction.getSelectedAmount());
            }
            return ResponseEntity.ok(response);
        }).orElse(ResponseEntity.notFound().build());
    }*/
    @PutMapping("/{transactionId}/status")
    public ResponseEntity<Void> updateTransactionStatus(
            @PathVariable String transactionId,
            @RequestParam String status) {
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Status: " + status);
        transactionService.updateTransactionStatus(transactionId, status);
        return ResponseEntity.ok().build();
    }

}
