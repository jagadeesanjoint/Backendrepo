package com.fidelity.mts.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fidelity.mts.entity.TransactionLog;

public interface TransactionRepo  extends JpaRepository<TransactionLog, Integer>{

}
