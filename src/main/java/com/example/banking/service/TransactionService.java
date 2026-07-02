package com.example.banking.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.banking.dto.request.TransactionRequest;
import com.example.banking.model.Account;
import com.example.banking.model.Transaction;
import com.example.banking.model.TransactionType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
  private final AccountRepository accountRepository;
  private final TransactionReposiotry transactionReposiotry;

  @Transactional
    public Transaction transfer(TransactionRequest request){
      Account accountTo = accountRepository.findById(request.toAccountId()).orElseThrow();
      Account accountFrom  = accountRepository.findById(request.fromAccountId()orElseThrow());

      if( (accountFrom.getBalance().subtract((request.amount()) < 0){
        throw new 
      }
      
      accountFrom.withdraw(request.amount);
      accountTo.deposit(request.amount);

      Transaction outgoing = Transaction.builder()
    .id(UUID.randomUUID())
    .account(accountFrom)
    .type(TransactionType.TRANSFER_OUT)
    .amount(request.amount())
    .createdAt(LocalDateTime.now())
    .note(request.note())
    .build();

    Transaction incoming = Transaction.builder()
    .id(UUID.randomUUID())
    .account(accountTo)
    .type(TransactionType.TRANSFER_IN)
    .amount(request.amount())
    .createdAt(LocalDateTime.now())
    .note(request.note())
    .build();
      
    }

    transactionRepository.save(outgoing);
    transactionRepository.save(incoming);
}
}
