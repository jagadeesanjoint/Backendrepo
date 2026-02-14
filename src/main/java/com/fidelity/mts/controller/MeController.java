package com.fidelity.mts.controller;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fidelity.mts.dto.AccountResponse;
import com.fidelity.mts.entity.TransactionLog;
import com.fidelity.mts.exceptions.AccountNotFoundException;
import com.fidelity.mts.repo.TransactionRepo;
import com.fidelity.mts.repo.UserRepo;
import com.fidelity.mts.service.AccountService;

/**
 * Current-user endpoints. No account id in URL — identity comes from Basic auth.
 * GET /api/v1/me → logged-in user's account
 * GET /api/v1/me/transactions → logged-in user's transaction log
 */
@RestController
@RequestMapping("/api/v1/me")
@CrossOrigin(origins = "*")
public class MeController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepo transactionRepo;

    @GetMapping
    public ResponseEntity<AccountResponse> getCurrentAccount(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        Long accountId = resolveAccountIdFromBasicAuth(authHeader);
        if (accountId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            AccountResponse account = accountService.getAccount(accountId);
            return ResponseEntity.ok(account);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionLog>> getCurrentTransactions(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        Long accountId = resolveAccountIdFromBasicAuth(authHeader);
        if (accountId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<TransactionLog> list = transactionRepo.findByFromAccountIDOrToAccountID(accountId, accountId);
        return ResponseEntity.ok(list);
    }

    /**
     * Parses Authorization: Basic base64(username:password), validates against username table,
     * returns user.id (which equals account.id) or null if invalid.
     */
    private Long resolveAccountIdFromBasicAuth(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return null;
        }
        String base64 = authHeader.substring(6).trim();
        if (base64.isEmpty()) {
            return null;
        }
        try {
            String decoded = new String(Base64.getDecoder().decode(base64));
            int colon = decoded.indexOf(':');
            if (colon < 0) {
                return null;
            }
            String username = decoded.substring(0, colon);
            String password = decoded.substring(colon + 1);
            return userRepo.findByUsername(username)
                    .filter(u -> password.equals(u.getPassword()))
                    .map(u -> u.getId())
                    .orElse(null);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
