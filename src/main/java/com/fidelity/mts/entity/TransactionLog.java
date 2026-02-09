package com.fidelity.mts.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import com.fidelity.mts.enums.TransactionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "mts.transaction_log")
public class TransactionLog {

	@Id
	@Column(length = 36)
	private UUID id;
	 
	@Column                                 //foreign key
	private Long fromAccountID;
	
	@Column                                 //foreign key
	private Long toAccountID;
	
	@Column(precision = 18, scale = 2)
	@NotNull
	private BigDecimal amount;
	
	@Column(length = 20)
	@NotNull
	private TransactionStatus status;
	
	@Column(length = 255, nullable = true)
	private String failureReason;
	
	@Column(length = 100, unique = true)
	private String idempotencyKey;
	
	@Column(name = "created_on", updatable = false, insertable = false)
	private Timestamp createdOn;
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getFromAccountID() {
		return fromAccountID;
	}

	public void setFromAccountID(Long fromAccountID) {
		this.fromAccountID = fromAccountID;
	}

	public Long getToAccountID() {
		return toAccountID;
	}

	public void setToAccountID(Long toAccountID) {
		this.toAccountID = toAccountID;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getIdempotencyKey() {
		return idempotencyKey;
	}

	public void setIdempotencyKey(String idempotencyKey) {
		this.idempotencyKey = idempotencyKey;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
}
