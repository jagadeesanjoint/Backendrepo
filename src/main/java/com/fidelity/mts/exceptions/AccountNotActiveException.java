package com.fidelity.mts.exceptions;

@SuppressWarnings("serial")
public class AccountNotActiveException extends RuntimeException {
	 public AccountNotActiveException() {
	     super("Account is LOCKED/CLOSED" );
	 }
	}
