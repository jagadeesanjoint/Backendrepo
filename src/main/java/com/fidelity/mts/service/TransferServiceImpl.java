package com.fidelity.mts.service;

import java.math.BigDecimal;
import java.util.UUID;

import com.fidelity.mts.dto.TransferRequest;
import com.fidelity.mts.dto.TransferResponse;
import com.fidelity.mts.entity.Account;
import com.fidelity.mts.entity.TransactionLog;
import com.fidelity.mts.enums.AccountStatus;
import com.fidelity.mts.enums.TransactionStatus;
import com.fidelity.mts.exceptions.AccountNotActiveException;
import com.fidelity.mts.exceptions.AccountNotFoundException;
import com.fidelity.mts.exceptions.DuplicateTransferException;
import com.fidelity.mts.exceptions.InsufficientBalanceException;
import com.fidelity.mts.exceptions.ValidationException;
import com.fidelity.mts.repo.AccountRepository;
import com.fidelity.mts.repo.TransactionLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TransferService per Progressive Project 1 spec.
 * transfer(), validateTransfer(), executeTransfer()
 * Business rules: accounts different, both exist, both ACTIVE, amount > 0, balance >= amount, idempotency unique.
 */
@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        String idempotencyKey = request.getIdempotencyKey();
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            idempotencyKey = UUID.randomUUID().toString();
            request.setIdempotencyKey(idempotencyKey);
        } else if (transactionLogRepository.existsByIdempotencyKey(idempotencyKey)) {
            throw new DuplicateTransferException();
        }
        validateTransfer(request);
        TransactionLog log = executeTransfer(request);
        return toResponse(log, request);
    }

    @Override
    public void validateTransfer(TransferRequest request) {
        if (request.getFromAccountId() != null && request.getFromAccountId().equals(request.getToAccountId())) {
            throw new ValidationException("Accounts must be different");
        }
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Amount must be greater than zero");
        }

        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(AccountNotFoundException::new);
        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(AccountNotFoundException::new);

        if (!fromAccount.isActive()) {
            throw new AccountNotActiveException();
        }
        if (!toAccount.isActive()) {
            throw new AccountNotActiveException();
        }
        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException();
        }
    }

    @Override
    @Transactional
    public TransactionLog executeTransfer(TransferRequest request) {
        Account fromAccount = accountRepository.findById(request.getFromAccountId()).orElseThrow(AccountNotFoundException::new);
        Account toAccount = accountRepository.findById(request.getToAccountId()).orElseThrow(AccountNotFoundException::new);
        BigDecimal amount = request.getAmount();

        fromAccount.debit(amount);
        accountRepository.save(fromAccount);

        toAccount.credit(amount);
        accountRepository.save(toAccount);

        TransactionLog log = new TransactionLog();
        log.setFromAccountId(fromAccount.getId());
        log.setToAccountId(toAccount.getId());
        log.setAmount(amount);
        log.setStatus(TransactionStatus.SUCCESS);
        log.setIdempotencyKey(request.getIdempotencyKey());
        transactionLogRepository.save(log);

        return log;
    }

    private TransferResponse toResponse(TransactionLog log, TransferRequest request) {
        TransferResponse r = new TransferResponse();
        r.setTransactionId("TRX-" + log.getId());
        r.setStatus(TransactionStatus.SUCCESS.name());
        r.setMessage("Transfer completed");
        r.setDebitedFrom(request.getFromAccountId());
        r.setCreditedTo(request.getToAccountId());
        r.setAmount(request.getAmount());
        return r;
    }
}
