package com.fidelity.mts.exceptions;

@SuppressWarnings("serial")
public class DuplicateTransferException extends RuntimeException {
	public DuplicateTransferException() {
		 super("Idempotency key reused");
	 }

}