package com.fidelity.mts.exceptions;

@SuppressWarnings("serial")
public class AccountNotFoundException extends RuntimeException {
	 public AccountNotFoundException() {
	     super("Account ID doesn't exist");
	 }
	}
