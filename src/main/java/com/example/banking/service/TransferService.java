package com.example.banking.service;

import com.example.banking.dto.response.TransferResponse;
import com.example.banking.exception.ResourceNotFoundException;
import com.example.banking.model.Transfer;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.TransferRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;

    public TransferService(TransferRepository transferRepository,
                           AccountRepository accountRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }

    public List<TransferResponse> getAccountStatement(String accountId) {

        UUID accountIdUuid = UUID.fromString(accountId);

        accountRepository.findById(accountIdUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found"));

        return transferRepository.findByAccountId(accountIdUuid)
                .stream()
                .sorted(Comparator.comparing(Transfer::getCreatedAt).reversed())
                .map(TransferResponse::fromEntity)
                .toList();
    }
}