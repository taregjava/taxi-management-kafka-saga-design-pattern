package com.halfacode.taxi.transaction.service;

import com.halfacode.taxi.transaction.dto.EventDto;
import com.halfacode.taxi.transaction.entity.Account;
import com.halfacode.taxi.transaction.repository.AccountRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionConsumer {

    @Autowired
    private AccountRepository accountRepository;

    @KafkaListener(topics = "account-transactions", groupId = "transaction-group")
    @Transactional
    public void consumeTransaction(EventDto event) {
        System.out.println("Received event: " + event);


        if ("debit".equals(event.getEventType())) {
            handleDebit(event);
        } else if ("credit".equals(event.getEventType())) {
            handleCredit(event);
        }
    }

    private void handleDebit(EventDto event) {
        Account account = accountRepository.findById(event.getFromAccount())
                .orElseThrow(() -> new RuntimeException("Account not found: " + event.getFromAccount()));

        if (account.getBalance() < event.getAmount()) {
            throw new RuntimeException("Insufficient balance in account: " + event.getFromAccount());
        }


        account.setBalance(account.getBalance() - event.getAmount());
        accountRepository.save(account);

        System.out.println("Debited " + event.getAmount() + " from account " + event.getFromAccount());
    }

    private void handleCredit(EventDto event) {
        Account account = accountRepository.findById(event.getToAccount())
                .orElseThrow(() -> new RuntimeException("Account not found: " + event.getToAccount()));
        account.setBalance(account.getBalance() + event.getAmount());
        accountRepository.save(account);
        System.out.println("Credited " + event.getAmount() + " to account " + event.getToAccount());
    }
}
