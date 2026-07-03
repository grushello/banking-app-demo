package com.example.banking.repository;

import com.example.banking.model.Account;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountRepository {
    private final Map<UUID, Account> accounts = new ConcurrentHashMap<>();
    
    public Account save(Account account) {
        accounts.put(account.getId(), account);
        return account;
    }
    
    public Optional<Account> findById(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }
}
