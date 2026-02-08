package com.fidelity.mts.service;

import java.math.BigDecimal;
import java.util.List;
import com.fidelity.mts.entity.Account;
import com.fidelity.mts.entity.TransactionLog;

public interface AccountService{
	Account getAccount(int id);
	BigDecimal getBalance(int id);
	List<TransactionLog> getTransactions(int id);
}
