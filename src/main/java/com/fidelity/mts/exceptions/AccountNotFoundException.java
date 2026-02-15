package com.fidelity.mts.exceptions;

@SuppressWarnings("serial")
public class AccountNotFoundException extends RuntimeException {

    public static final String ERROR_CODE = "ACC_404";

    public AccountNotFoundException() {
        super("Account not found");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
