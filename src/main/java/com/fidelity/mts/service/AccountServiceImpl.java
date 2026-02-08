package com.fidelity.mts.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fidelity.mts.entity.Account;
import com.fidelity.mts.entity.TransactionLog;
import com.fidelity.mts.exceptions.AccountNotFoundException;
import com.fidelity.mts.repo.AccountRepo;

@Service
public class AccountServiceImpl implements AccountService{
	@Autowired AccountRepo repo;

	@Override
	public Account getAccount(int id) {
		Optional<Account> optional = repo.findById(id);
		if(!optional.isPresent()) {
			throw new AccountNotFoundException();
		}
		return optional.get();
	}

	@Override
	public BigDecimal getBalance(int id) {
		return null;
	}

	@Override
	public List<TransactionLog> getTransactions(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
