package com.halfacode.taxi.service;

import com.halfacode.taxi.entity.ProcessedMessage;
import com.halfacode.taxi.entity.Transaction;
import com.halfacode.taxi.repository.ProcessedMessageRepository;
import com.halfacode.taxi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ProcessedMessageRepository processedMessageRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Transaction saveTransaction(Transaction transaction) {
        // Check if a transaction with the same ID already exists
        Transaction existingTransaction = transactionRepository.findByTransactionId(transaction.getTransactionId());
        if (existingTransaction != null) {
            existingTransaction.setPaidAmount(existingTransaction.getPaidAmount());
            existingTransaction.setStatus(transaction.getStatus());
            existingTransaction.setCreatedAt(transaction.getCreatedAt());
            return transactionRepository.save(existingTransaction);
        } else {
            return transactionRepository.save(transaction);
        }


    }


    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction findByTransactionId(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId);
    }

    public void updateTransactionStatus(String transactionId, String status) {
        Transaction transaction = transactionRepository.findByTransactionId(transactionId);
        if (transaction != null) {
            transaction.setStatus(status);
            transactionRepository.save(transaction);
        }
    }
  /*  public void markMessageAsProcessed(String messageId) {
        ProcessedMessage processedMessage = new ProcessedMessage();
        processedMessage.setMessageId(messageId);
        processedMessageRepository.save(processedMessage);
    }*/
  public void markMessageAsProcessed(String messageId) {
      redisTemplate.opsForValue().set(messageId, "processed", Duration.ofMinutes(10));
  }
    public boolean isMessageProcessed(String messageId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(messageId));
    }
}
