package com.fidelity.mts.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fidelity.mts.entity.Account;
import com.fidelity.mts.entity.TransactionLog;
import com.fidelity.mts.service.AccountService;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
	@Autowired
	AccountService service;
	
	@GetMapping("/{id}")
	public String getAccount(int id) {
		service.getAccount(id);
		return "Account retrieved successfully";
	}

	@GetMapping("/{id}/balance")
	public BigDecimal getBalance(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping("/{id}/transactions")
	public List<TransactionLog> getTransactions(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
