package com.fidelity.mts.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

import com.fidelity.mts.enums.AccountStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "account")
public class Account {

	@Id                                                  //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //AutoIncrement
	private Long id;     
	
	@Column(length = 225)                //VARCHAR(255)
	@NotNull
    private String holderName;     
	
	@Column(precision = 18, scale = 2)   //Decimal(18,2)
	@NotNull
    private BigDecimal balance; 
    
    @Column(length = 20)
	@NotNull
    private AccountStatus status; 
    
    @Column(precision = 0)               //Default = 0
	private Integer version;       
    
    @UpdateTimestamp
    @Column(name = "last_updated")       //update time automatically
    private Timestamp lastUpdated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public void debit() {
		
	}
	
	public void credit() {
		
	}
	public void isActive() {
		
	}
}
