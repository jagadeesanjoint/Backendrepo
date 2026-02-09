package com.fidelity.mts.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fidelity.mts.dto.AccountResponse;
import com.fidelity.mts.entity.Account;
import com.fidelity.mts.entity.TransactionLog;
import com.fidelity.mts.repo.TransactionRepo;
import com.fidelity.mts.service.AccountService;

@RestController
@RequestMapping("/api/v1/accounts")
@CrossOrigin(origins = "*")
public class AccountController {
	@Autowired
	AccountService service;
	
	@Autowired 
	private TransactionRepo transactionRepo;
	
	@Autowired
    public AccountController(AccountService accountService) {
        this.service = accountService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long id) {
        //log.info("Fetching account with ID: {}", id);
        
        AccountResponse account = service.getAccount(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long id) {
        BigDecimal balance = service.getBalance(id);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/{id}/transactions") public List<TransactionLog> getTransactions(@PathVariable("id") Long id) { 
    	return transactionRepo.findByFromAccountIDOrToAccountID(id, id); }

}
