package com.fidelity.mts.exceptions;

@SuppressWarnings("serial")
public class InsufficientBalanceException extends RuntimeException {

    public static final String ERROR_CODE = "TRX_400";

    public InsufficientBalanceException() {
        super("Insufficient balance");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
