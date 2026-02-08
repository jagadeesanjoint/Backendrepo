package com.fidelity.mts.exceptions;

@SuppressWarnings("serial")
public class InsufficientBalanceException extends RuntimeException {
	 public InsufficientBalanceException() {
		 super("Balance < transfer amount");
	 }
}