package com.example.banking.repository;

import com.example.banking.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository {
    private final List<Transaction> transactions = new CopyOnWriteArrayList<>();

    public Transaction save(Transaction transaction) {
        transactions.add(transaction);
        return transaction;
    }

    public List<Transaction> findByAccountId(UUID accountId) {
        return transactions.stream()
                .filter(t -> t.getAccount().getId().equals(accountId))
                .collect(Collectors.toList());
    }
}
