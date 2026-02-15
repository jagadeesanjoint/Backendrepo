package com.fidelity.mts.controller;

import java.util.Base64;

import com.fidelity.mts.dto.Login;
import com.fidelity.mts.dto.LoginResponse;
import com.fidelity.mts.exceptions.AccountNotFoundException;
import com.fidelity.mts.service.AccountService;
import com.fidelity.mts.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Login component per Progressive Project 1 spec.
 * Auth Service: login() - returns accountId, holderName, token for Angular getToken().
 * Password verification with BCrypt.
 */
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        if (login.getUsername() == null || login.getUsername().isBlank()
                || login.getPassword() == null || login.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthError("Username and password are required."));
        }
        return userRepository.findByUsername(login.getUsername().trim())
                .filter(user -> passwordEncoder.matches(login.getPassword(), user.getPassword()))
                .map(user -> {
                    try {
                        Long accountId = user.getId();
                        String holderName = accountService.getAccount(accountId).getHolderName();
                        String token = Base64.getEncoder().encodeToString(
                                (login.getUsername().trim() + ":" + login.getPassword()).getBytes());
                        return ResponseEntity.ok(new LoginResponse(accountId, holderName, token));
                    } catch (AccountNotFoundException e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(new AuthError("No account linked to this user."));
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthError("Invalid username or password.")));
    }

    public static class AuthError {
        private final String message;

        public AuthError(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
