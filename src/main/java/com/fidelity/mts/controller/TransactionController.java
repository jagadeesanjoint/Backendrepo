package com.fidelity.mts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fidelity.mts.dto.TransferRequest;
import com.fidelity.mts.entity.TransactionLog;
import com.fidelity.mts.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v")
public class TransactionController {

    @Autowired
    private TransactionService transferService;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionLog> transfer(@Valid @RequestBody TransferRequest request) {
        TransactionLog log = transferService.transfer(request);
        return ResponseEntity.ok(log);
    }
}
