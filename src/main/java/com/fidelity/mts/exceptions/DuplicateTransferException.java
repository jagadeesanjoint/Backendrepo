package com.fidelity.mts.exceptions;

@SuppressWarnings("serial")
public class DuplicateTransferException extends RuntimeException {

    public static final String ERROR_CODE = "TRX_409";

    public DuplicateTransferException() {
        super("Duplicate transfer");
    }

    public DuplicateTransferException(String message) {
        super(message);
    }
}
