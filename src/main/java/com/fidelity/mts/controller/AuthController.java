package com.fidelity.mts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fidelity.mts.dto.Login;
import com.fidelity.mts.dto.LoginResponse;
import com.fidelity.mts.entity.User;
import com.fidelity.mts.exceptions.AccountNotFoundException;
import com.fidelity.mts.repo.UserRepo;
import com.fidelity.mts.service.AccountService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AccountService accountService;

    /**
     * Login only with credentials that exist in the username table.
     * username.id must match account.id (1-to-1). On success returns accountId and holderName
     * so dashboard shows that account and history shows that account's transaction_log.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        if (login.getUsername() == null || login.getUsername().isBlank()
                || login.getPassword() == null || login.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorMessage("Username and password are required."));
        }
        return userRepo.findByUsername(login.getUsername().trim())
                .filter(user -> login.getPassword().equals(user.getPassword()))
                .map(user -> {
                    try {
                        Long accountId = user.getId();
                        String holderName = accountService.getAccount(accountId).getHolderName();
                        return ResponseEntity.ok(new LoginResponse(accountId, holderName));
                    } catch (AccountNotFoundException e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(new ErrorMessage("No account linked to this user."));
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorMessage("Invalid username or password.")));
    }

    public static class ErrorMessage {
        private final String message;
        public ErrorMessage(String message) { this.message = message; }
        public String getMessage() { return message; }
    }
}
