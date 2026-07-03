package com.example.banking.repository;

import com.example.banking.model.Account;
import com.example.banking.model.Transaction;
import com.example.banking.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTests {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();
        transactionRepository = new TransactionRepository();
    }

    @Test
    void testAccountRepository() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "TR12345", "John Doe", BigDecimal.ZERO);
        
        accountRepository.save(account);
        
        Optional<Account> found = accountRepository.findById(id);
        assertTrue(found.isPresent());
        assertEquals("John Doe", found.get().getOwnerName());
        
        Optional<Account> notFound = accountRepository.findById(UUID.randomUUID());
        assertFalse(notFound.isPresent());
    }

    @Test
    void testTransactionRepository() {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, "TR12345", "John Doe", BigDecimal.ZERO);
        
        Transaction t1 = new Transaction(UUID.randomUUID(), account, TransactionType.DEPOSIT, new BigDecimal("100"), LocalDateTime.now(), "Deposit 1");
        Transaction t2 = new Transaction(UUID.randomUUID(), account, TransactionType.WITHDRAWAL, new BigDecimal("50"), LocalDateTime.now(), "Withdraw 1");
        
        Account otherAccount = new Account(UUID.randomUUID(), "TR99999", "Jane Doe", BigDecimal.ZERO);
        Transaction t3 = new Transaction(UUID.randomUUID(), otherAccount, TransactionType.DEPOSIT, new BigDecimal("200"), LocalDateTime.now(), "Other deposit");
        
        transactionRepository.save(t1);
        transactionRepository.save(t2);
        transactionRepository.save(t3);
        
        List<Transaction> accountTransactions = transactionRepository.findByAccountId(accountId);
        assertEquals(2, accountTransactions.size());
        assertTrue(accountTransactions.contains(t1));
        assertTrue(accountTransactions.contains(t2));
        assertFalse(accountTransactions.contains(t3));
    }
}
