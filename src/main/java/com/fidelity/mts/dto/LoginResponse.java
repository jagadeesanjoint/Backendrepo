package com.fidelity.mts.dto;

/**
 * Login response for Angular: accountId, holderName, token (for getToken() / auth header).
 */
public class LoginResponse {

    private Long accountId;
    private String holderName;
    private String token;

    public LoginResponse() {}

    public LoginResponse(Long accountId, String holderName, String token) {
        this.accountId = accountId;
        this.holderName = holderName;
        this.token = token;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
