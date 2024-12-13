package com.halfacode.taxi.transaction.controller;

import com.halfacode.taxi.transaction.entity.Account;
import com.halfacode.taxi.transaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{accountId}")
    public Account getAccountById(@PathVariable String accountId) {
        return accountService.getAccountById(accountId);
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @PutMapping("/{accountId}")
    public Account updateAccount(@PathVariable String accountId, @RequestBody Account updatedAccount) {
        return accountService.updateAccount(accountId, updatedAccount);
    }

    @DeleteMapping("/{accountId}")
    public void deleteAccount(@PathVariable String accountId) {
        accountService.deleteAccount(accountId);
    }
}
