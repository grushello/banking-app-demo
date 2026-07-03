package com.example.banking.service;

import com.example.banking.dto.response.TransactionResponse;
import com.example.banking.exception.ResourceNotFoundException;
import com.example.banking.model.Account;
import com.example.banking.model.Transaction;
import com.example.banking.model.TransactionType;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();
        transactionRepository = new TransactionRepository();
        transactionService = new TransactionService(transactionRepository, accountRepository);
    }

    @Test
    void shouldReturnTransactionsSortedByNewestFirst() {

        // Arrange
        UUID accountId = UUID.randomUUID();

        Account account = new Account(
                accountId,
                "TR12345",
                "John Doe",
                BigDecimal.ZERO
        );

        accountRepository.save(account);

        Transaction olderTransaction = new Transaction(
                UUID.randomUUID(),
                account,
                TransactionType.DEPOSIT,
                new BigDecimal("100"),
                LocalDateTime.now().minusDays(1),
                "Older deposit"
        );

        Transaction newerTransaction = new Transaction(
                UUID.randomUUID(),
                account,
                TransactionType.WITHDRAWAL,
                new BigDecimal("50"),
                LocalDateTime.now(),
                "New withdrawal"
        );

        transactionRepository.save(olderTransaction);
        transactionRepository.save(newerTransaction);

        // Act
        List<TransactionResponse> result =
                transactionService.getAccountStatement(accountId.toString());

        // Assert
        assertEquals(2, result.size());
        assertEquals("New withdrawal", result.get(0).note());
        assertEquals("Older deposit", result.get(1).note());
    }

    @Test
    void shouldReturnEmptyListWhenAccountHasNoTransactions() {

        // Arrange
        UUID accountId = UUID.randomUUID();

        Account account = new Account(
                accountId,
                "TR12345",
                "John Doe",
                BigDecimal.ZERO
        );

        accountRepository.save(account);

        // Act
        List<TransactionResponse> result =
                transactionService.getAccountStatement(accountId.toString());

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {

        // Arrange
        UUID accountId = UUID.randomUUID();

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> transactionService.getAccountStatement(accountId.toString())
        );
    }

    @Test
    void shouldMapTransactionToTransactionResponse() {

        // Arrange
        UUID accountId = UUID.randomUUID();

        Account account = new Account(
                accountId,
                "TR12345",
                "John Doe",
                BigDecimal.ZERO
        );

        accountRepository.save(account);

        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                account,
                TransactionType.DEPOSIT,
                new BigDecimal("250"),
                LocalDateTime.now(),
                "Salary"
        );

        transactionRepository.save(transaction);

        // Act
        List<TransactionResponse> result =
                transactionService.getAccountStatement(accountId.toString());

        // Assert
        TransactionResponse response = result.getFirst();

        assertEquals(transaction.getId().toString(), response.id());
        assertEquals(transaction.getType().name(), response.type());
        assertEquals(transaction.getAmount(), response.amount());
        assertEquals(transaction.getNote(), response.note());
    }
}