package com.example.banking.service;

import com.example.banking.dto.response.TransactionResponse;
import com.example.banking.exception.ResourceNotFoundException;
import com.example.banking.model.Transaction;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<TransactionResponse> getAccountStatement(String accountId) {

        UUID accountIdUuid = UUID.fromString(accountId);

        accountRepository.findById(accountIdUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found"));

        return transactionRepository.findByAccountId(accountIdUuid)
                .stream()
                .sorted(Comparator.comparing(Transaction::getCreatedAt).reversed())
                .map(TransactionResponse::fromEntity)
                .toList();
    }
}