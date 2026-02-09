package com.fidelity.mts.service;

import java.math.BigDecimal;
import java.util.List;

import com.fidelity.mts.dto.AccountResponse;
import com.fidelity.mts.entity.Account;
import com.fidelity.mts.entity.TransactionLog;

public interface AccountService{
	AccountResponse getAccount(Long id);
	BigDecimal getBalance(Long id);
	List<TransactionLog> getTransactions(Long id);
}
