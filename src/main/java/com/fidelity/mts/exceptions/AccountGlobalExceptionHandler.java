package com.fidelity.mts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountGlobalExceptionHandler {
	@ExceptionHandler (value = AccountNotFoundException.class)
	public ResponseEntity<String> accountNotFound(AccountNotFoundException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler (value = AccountNotActiveException.class)
	public ResponseEntity<String> accountNotActiveException(AccountNotActiveException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler (value = DuplicateTransferException.class)
	public ResponseEntity<String> duplicateTransferException(DuplicateTransferException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler (value = InsufficientBalanceException.class)
	public ResponseEntity<String> insufficientBalanceException(InsufficientBalanceException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
}

