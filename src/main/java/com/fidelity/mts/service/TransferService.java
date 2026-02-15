package com.fidelity.mts.service;

import com.fidelity.mts.dto.TransferRequest;
import com.fidelity.mts.dto.TransferResponse;
import com.fidelity.mts.entity.TransactionLog;

public interface TransferService {

    TransferResponse transfer(TransferRequest request);

    void validateTransfer(TransferRequest request);

    TransactionLog executeTransfer(TransferRequest request);
}
