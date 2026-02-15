package com.fidelity.mts.exceptions;

@SuppressWarnings("serial")
public class ValidationException extends RuntimeException {

    public static final String ERROR_CODE = "VAL_422";

    public ValidationException(String message) {
        super(message);
    }
}
