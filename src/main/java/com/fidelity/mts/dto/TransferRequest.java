package com.fidelity.mts.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class TransferRequest {

    @NotNull(message = "Source account ID is required")
    private Long fromAccountId;

    @NotNull(message = "Destination account ID is required")
    private Long toAccountId;

    @NotNull(message = "Transfer amount is required")
    @Positive(message = "Transfer amount must be greater than zero")
    private BigDecimal amount;

    // Optional; if not provided, the server generates a unique idempotency key automatically
    @Size(max = 100, message = "Idempotency key must not exceed 100 characters")
    private String idempotencyKey;

    public TransferRequest() {}

    public TransferRequest(Long fromAccountId, Long toAccountId, BigDecimal amount, String idempotencyKey) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.idempotencyKey = idempotencyKey;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}
