package com.fidelity.mts.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.mts.dto.AccountResponse;
import com.fidelity.mts.entity.Account;
import com.fidelity.mts.entity.TransactionLog;
import com.fidelity.mts.exceptions.AccountNotFoundException;
import com.fidelity.mts.repo.AccountRepo;

@Service
public class AccountServiceImpl implements AccountService{
	@Autowired AccountRepo repo;

	@Override
	public AccountResponse getAccount(Long id) {
//		log.info("Fetching account details for ID: {}", id);
 
        Account account = repo.findById(id)
                .orElseThrow(() -> new AccountNotFoundException());
 
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setHolderName(account.getHolderName());
        response.setBalance(account.getBalance());
        response.setStatus(account.getStatus().name());
 
        return response;
    }

	public BigDecimal getBalance(Long accountId) {
        Account account = repo.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException());
        
        return account.getBalance();
    }

	@Override
	public List<TransactionLog> getTransactions(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
