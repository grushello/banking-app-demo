package com.example.banking.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.banking.dto.request.TransferRequest;
import com.example.banking.dto.response.TransferResponse;
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
  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;

  public synchronized TransferResponse transfer(TransferRequest request) {
    Account accountFrom = accountRepository.findById(request.fromAccountId())
        .orElseThrow(() -> new ResourceNotFoundException("Source account not found"));

    Account accountTo = accountRepository.findById(request.toAccountId())
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

    return TransferResponse.from(outgoing, incoming);
  }
}
