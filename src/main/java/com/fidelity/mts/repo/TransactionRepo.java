package com.fidelity.mts.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fidelity.mts.entity.TransactionLog;

public interface TransactionRepo extends JpaRepository<TransactionLog, UUID>{
	boolean existsByIdempotencyKey(String idempotencyKey);
	List<TransactionLog> findByFromAccountIDOrToAccountID(Long fromAccountID, Long toAccountID);

}
