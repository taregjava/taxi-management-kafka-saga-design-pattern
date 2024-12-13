package com.halfacode.taxi.transaction.service;

import com.halfacode.taxi.transaction.entity.Account;
import com.halfacode.taxi.transaction.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(String accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountId));
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(String accountId, Account updatedAccount) {
        Account account = getAccountById(accountId);
        account.setName(updatedAccount.getName());
        account.setBalance(updatedAccount.getBalance());
        return accountRepository.save(account);
    }

    public void deleteAccount(String accountId) {
        accountRepository.deleteById(accountId);
    }
}