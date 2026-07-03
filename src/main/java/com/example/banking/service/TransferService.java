package com.example.banking.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.banking.dto.request.TransferRequestDTO;
import com.example.banking.dto.response.TransferResponseDTO;
import com.example.banking.exception.InsufficientFundsException;
import com.example.banking.exception.ResourceNotFoundException;
import com.example.banking.model.Account;
import com.example.banking.model.Transaction;
import com.example.banking.model.TransactionType;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransferService {
  private final AccountService accountService;
  private final TransactionRepository transactionRepository;

  public synchronized TransferResponseDTO transfer(TransferRequestDTO request) {
    Account accountFrom = accountService.getAccount(request.fromAccountId())
        .orElseThrow(() -> new ResourceNotFoundException("Source account not found"));

    Account accountTo = accountService.getAccount(request.toAccountId())
        .orElseThrow(() -> new ResourceNotFoundException("Destination account not found"));

    if (accountFrom.getBalance().compareTo(request.amount()) < 0) {
      throw new InsufficientFundsException("Insufficient funds");
    }

    accountFrom.withdraw(request.amount());
    accountTo.deposit(request.amount());

    LocalDateTime createdAt = LocalDateTime.now();

    Transaction outgoing = Transaction.builder()
        .id(UUID.randomUUID())
        .account(accountFrom)
        .type(TransactionType.TRANSFER_OUT)
        .amount(request.amount())
        .createdAt(createdAt)
        .note(request.note())
        .build();

    Transaction incoming = Transaction.builder()
        .id(UUID.randomUUID())
        .account(accountTo)
        .type(TransactionType.TRANSFER_IN)
        .amount(request.amount())
        .createdAt(createdAt)
        .note(request.note())
        .build();

    transactionRepository.save(outgoing);
    transactionRepository.save(incoming);

    String transferTransactionId = outgoing.getId() + "-" + incoming.getId();
    return TransferResponseDTO.success(transferTransactionId, createdAt);
  }
}
