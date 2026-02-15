package com.fidelity.mts.exceptions;

@SuppressWarnings("serial")
public class AccountNotActiveException extends RuntimeException {

    public static final String ERROR_CODE = "ACC_403";

    public AccountNotActiveException() {
        super("Account not active");
    }

    public AccountNotActiveException(String message) {
        super(message);
    }
}
