package com.example.banking.service;

import com.example.banking.dto.response.TransferResponse;
import com.example.banking.exception.InsufficientFundsException;
import com.example.banking.exception.InvalidTransferException;
import com.example.banking.exception.ResourceNotFoundException;
import com.example.banking.model.Transfer;
import com.example.banking.model.TransferType;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.TransferRepository;
import org.springframework.stereotype.Service;
import com.example.banking.dto.request.TransferRequest;
import com.example.banking.model.Account;

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

    public List<TransferResponse> getAccountStatement(UUID accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        return transferRepository.findByUserId(accountId)
                .stream()
                .sorted(Comparator.comparing(Transfer::getCreatedAt).reversed())
                .map(TransferResponse::fromEntity)
                .toList();
    }

    public List<TransferResponse> transfer(TransferRequest request) {

        UUID fromId = UUID.fromString(request.fromAccountId());
        UUID toId = UUID.fromString(request.toAccountId());

        if (fromId.equals(toId)) {
            throw new InvalidTransferException("Sender and recipient accounts must be different");
        }

        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new ResourceNotFoundException("Source account not found"));

        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new ResourceNotFoundException("Destination account not found"));

        if (from.getBalance().compareTo(request.amount()) < 0) {
            throw new InsufficientFundsException("Insufficient balance");
        }

        from.withdraw(request.amount());
        to.deposit(request.amount());

        Transfer out = Transfer.builder()
                .id(UUID.randomUUID())
                .account(from)
                .type(TransferType.TRANSFER_OUT)
                .amount(request.amount())
                .createdAt(java.time.LocalDateTime.now())
                .note(request.note())
                .build();

        Transfer in = Transfer.builder()
                .id(UUID.randomUUID())
                .account(to)
                .type(TransferType.TRANSFER_IN)
                .amount(request.amount())
                .createdAt(java.time.LocalDateTime.now())
                .note(request.note())
                .build();

        transferRepository.save(out);
        transferRepository.save(in);

        return List.of(
                TransferResponse.fromEntity(out),
                TransferResponse.fromEntity(in)
        );
    }
}