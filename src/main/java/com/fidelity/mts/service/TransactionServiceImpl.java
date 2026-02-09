package com.fidelity.mts.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.mts.dto.TransferRequest;
import com.fidelity.mts.entity.Account;
import com.fidelity.mts.entity.TransactionLog;
import com.fidelity.mts.enums.AccountStatus;
import com.fidelity.mts.enums.TransactionStatus;
import com.fidelity.mts.exceptions.AccountNotActiveException;
import com.fidelity.mts.exceptions.AccountNotFoundException;
import com.fidelity.mts.exceptions.DuplicateTransferException;
import com.fidelity.mts.exceptions.InsufficientBalanceException;
import com.fidelity.mts.repo.AccountRepo;
import com.fidelity.mts.repo.TransactionRepo;

import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepo accountRepository;

    @Autowired
    private TransactionRepo transactionLogRepository;

    @Transactional
    @Override
    public TransactionLog transfer(TransferRequest request) {
        validateTransfer(request);
        return executeTransfer(request);
    }

    @Override
    public void validateTransfer(TransferRequest request) {
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InsufficientBalanceException();
        }

        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new AccountNotFoundException());

        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new AccountNotFoundException());

        if (fromAccount.getStatus() != AccountStatus.ACTIVE || toAccount.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException();
        }
        
        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) { 
        	throw new InsufficientBalanceException(); 
        	}

        // Idempotency check
        if (transactionLogRepository.existsByIdempotencyKey(request.getIdempotencyKey())) {
            throw new DuplicateTransferException();
        }
    }

    @Override
    public TransactionLog executeTransfer(TransferRequest request) {
        Account fromAccount = accountRepository.findById(request.getFromAccountId()).get();
        Account toAccount = accountRepository.findById(request.getToAccountId()).get();

        // Debit source
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        accountRepository.save(fromAccount);

        // Credit destination
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
        accountRepository.save(toAccount);

        // Log transaction
        TransactionLog log = new TransactionLog();
        log.setId(UUID.randomUUID());
        log.setFromAccountID(fromAccount.getId());
        log.setToAccountID(toAccount.getId());
        log.setAmount(request.getAmount());
        log.setStatus(TransactionStatus.SUCCESS);
        log.setIdempotencyKey(request.getIdempotencyKey());

        transactionLogRepository.save(log);

        return log;
    }
}
