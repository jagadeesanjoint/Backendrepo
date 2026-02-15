package com.fidelity.mts.repo;

import com.fidelity.mts.entity.TransactionLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, String> {

    boolean existsByIdempotencyKey(String idempotencyKey);

    List<TransactionLog> findByFromAccountIdOrToAccountId(Long fromAccountId, Long toAccountId);
}
