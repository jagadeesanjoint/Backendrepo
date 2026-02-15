package com.fidelity.mts.controller;

import java.math.BigDecimal;
import java.util.List;

import com.fidelity.mts.dto.AccountResponse;
import com.fidelity.mts.entity.TransactionLog;
import com.fidelity.mts.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller per Progressive Project 1 spec.
 * GET /api/v1/accounts/{id}, /api/v1/accounts/{id}/balance, /api/v1/accounts/{id}/transactions
 */
@RestController
@RequestMapping("/api/v1/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long id) {
        AccountResponse account = accountService.getAccount(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long id) {
        BigDecimal balance = accountService.getBalance(id);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionLog>> getTransactions(@PathVariable Long id) {
        List<TransactionLog> transactions = accountService.getTransactions(id);
        return ResponseEntity.ok(transactions);
    }
}
