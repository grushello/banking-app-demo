package com.example.banking.repository;

import com.example.banking.model.Account;
import com.example.banking.model.Transfer;
import com.example.banking.model.TransferType;
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
    private TransferRepository transferRepository;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();
        transferRepository = new TransferRepository();
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
    void testTransferRepository() {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, "TR12345", "John Doe", BigDecimal.ZERO);
        
        Transfer t1 = new Transfer(UUID.randomUUID(), account, TransferType.DEPOSIT, new BigDecimal("100"), LocalDateTime.now(), "Deposit 1");
        Transfer t2 = new Transfer(UUID.randomUUID(), account, TransferType.WITHDRAWAL, new BigDecimal("50"), LocalDateTime.now(), "Withdraw 1");
        
        Account otherAccount = new Account(UUID.randomUUID(), "TR99999", "Jane Doe", BigDecimal.ZERO);
        Transfer t3 = new Transfer(UUID.randomUUID(), otherAccount, TransferType.DEPOSIT, new BigDecimal("200"), LocalDateTime.now(), "Other deposit");
        
        transferRepository.save(t1);
        transferRepository.save(t2);
        transferRepository.save(t3);
        
        List<Transfer> accountTransfers = transferRepository.findByUserId(accountId);
        assertEquals(2, accountTransfers.size());
        assertTrue(accountTransfers.contains(t1));
        assertTrue(accountTransfers.contains(t2));
        assertFalse(accountTransfers.contains(t3));
    }
}
